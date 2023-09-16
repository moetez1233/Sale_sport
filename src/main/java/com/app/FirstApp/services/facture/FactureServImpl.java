package com.app.FirstApp.services.facture;

import com.app.FirstApp.config.customException.InvalidResourceException;
import com.app.FirstApp.config.customException.NotExisteException;
import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.mapper.detailFactureMapper.DetailFactureMapperService;
import com.app.FirstApp.mapper.factureMapper.FactureMapperService;
import com.app.FirstApp.modele.facture.DetailFactureDto;
import com.app.FirstApp.modele.facture.FactureDto;
import com.app.FirstApp.repository.facture.DetailFactureRepo;
import com.app.FirstApp.repository.facture.FactureRepo;
import com.app.FirstApp.repository.produit.ProduitRepo;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.produit.ProduitService;
import com.app.FirstApp.services.userRole.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FactureServImpl implements FactureService {
    @Autowired
    private FactureRepo factureRepo;
    @Autowired
    private DetailFactureRepo detailFactureRepo;
    @Autowired
    private ActeurServ acteurServ;
    @Autowired
    private UserService userService;
    @Autowired
    private ProduitRepo produitRepo;
    @Autowired
    private FactureMapperService factureMapperService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private DetailFactureServ detailFactureServ;
    @Autowired
    DetailFactureMapperService detailFactureMapperService;


    @Override
    @Transactional
    public FactureDto updateFactureDto(FactureDto factureDto) {
        Facture facture = factureMapperService.dtoFactureToEntity(factureDto);
        Set<Long> idsDetailToUpdate=factureDto.getDetailFactures().stream().map(d -> d.getId()).collect(Collectors.toSet());
        Set<DetailFacture> detailFactures=detailFactureServ.getSetDetailFactures(factureDto.getId());
        Set<Long> idsDetailToDelete=new HashSet<>();
        detailFactures.forEach(d -> {
            if(!idsDetailToUpdate.contains(d.getId())){
                idsDetailToDelete.add(d.getId());
            }
        });
        detailFactureServ.deleteDetailFacture(new ArrayList<>(idsDetailToDelete));

        List<DetailFacture> detailFacturesList = detailFactureMapperService.ListDtoToEntity(factureDto.getDetailFactures());
        facture.setActeur(acteurServ.getUserConnected());
        Facture factureSaved = factureRepo.save(facture);
        List<Produits> produitsListUpdatedQuantite = new ArrayList<>();
        detailFacturesList.forEach(detailFact -> {
                produitsListUpdatedQuantite.add(detailFact.getProduits());
                detailFact.setLibelleProduit(detailFact.getProduits().getLibell());
                detailFact.setCodeProduit(detailFact.getProduits().getCode());

        });
        detailFactureRepo.saveAll(detailFacturesList);
        produitRepo.saveAll(produitsListUpdatedQuantite);
        return factureMapperService.entityFactureToDto(factureSaved);
    }


    @Override
    @Transactional
    public FactureDto saveFactureDto(FactureDto factureDto) {
        Acteur acteur = acteurServ.getUserConnected();
        List<DetailFacture> detailFacturesList = new ArrayList<>();
        List<Produits> produitsListUpdatedQuantite = new ArrayList<>();
        List<DetailFactureDto> detailFacturesDto = factureDto.getDetailFactures();
        factureDto.setNumero(getNumeroFacture(acteur.getId()));
        Facture facture = factureMapperService.dtoFactureToEntity(factureDto);
        facture.setActeur(acteurServ.getUserConnected());
        facture.setDateFacture(LocalDate.now());

        Facture factureSaved = factureRepo.save(facture);
        this.userService.verifUser();
        detailFacturesDto.forEach(fctDto -> {
            DetailFacture detailFacture = new DetailFacture();
            BigDecimal quantiteFacture = (fctDto.getProduits().getQuantite().subtract(fctDto.getQuantite()));
            if (quantiteFacture.compareTo(BigDecimal.ZERO) == 0 || quantiteFacture.compareTo(BigDecimal.ZERO) == 1) {
                fctDto.getProduits().setQuantite(quantiteFacture);
                produitsListUpdatedQuantite.add(fctDto.getProduits());
                detailFacture.setProduits(fctDto.getProduits());
                detailFacture.setQuantite(fctDto.getQuantite());
                detailFacture.setPrix(fctDto.getPrix());
                detailFacture.setFacture(factureSaved);
                detailFacture.setLibelleProduit(fctDto.getProduits().getLibell());
                detailFacture.setCodeProduit(fctDto.getProduits().getCode());
                detailFacturesList.add(detailFacture);
            } else {

                new RuntimeException("Quantite produit insufffisante");
            }
        });
        detailFactureRepo.saveAll(detailFacturesList);
        produitRepo.saveAll(produitsListUpdatedQuantite);
        return factureDto;
    }

    @Override
    public List<FactureDto> getListFactureDto() {
        Acteur acteur = acteurServ.getUserConnected();
        List<Facture> factureList = factureRepo.getListFactureByActeur(acteur.getId()).orElseThrow(() -> new NotExisteException("Utilisateur n'existe pas"));
        List<Long> idsFacture = factureList.stream().map(f -> f.getId()).collect(Collectors.toList());
        List<DetailFacture> detailFactures = detailFactureRepo.getAllByListIdsFacture(idsFacture).orElseThrow(() -> new NotExisteException("Details facture n'existe pas "));

        List<FactureDto> factureDtoList = factureMapperService.listEntityFactureToDto(factureList);
        this.userService.verifUser();
        factureDtoList.forEach(facDto -> {
            List<DetailFacture> detailFactures1 = detailFactures.stream().filter(df -> df.getFacture().getId().equals(facDto.getId())).collect(Collectors.toList());
            facDto.setDetailFactures(detailFactureMapperService.ListEntityToDto(detailFactures1));
        });
        return factureDtoList;
    }

    @Override
    public List<FactureDto> getListFactureDtoByWord(String word) {
        Acteur acteur = acteurServ.getUserConnected();
        List<Facture> factureList = factureRepo.getListFactureByActeurAndWords(acteur.getId(), word).orElseThrow(() -> new NotExisteException("Utilisateur n'existe pas"));
        List<Long> idsFacture = factureList.stream().map(f -> f.getId()).collect(Collectors.toList());
        List<DetailFacture> detailFactures = detailFactureRepo.getAllByListIdsFacture(idsFacture).orElseThrow(() -> new NotExisteException("Details facture n'existe pas "));

        List<FactureDto> factureDtoList = factureMapperService.listEntityFactureToDto(factureList);
        this.userService.verifUser();
        factureDtoList.forEach(facDto -> {
            List<DetailFacture> detailFactures1 = detailFactures.stream().filter(df -> df.getFacture().getId().equals(facDto.getId())).collect(Collectors.toList());
            facDto.setDetailFactures(detailFactureMapperService.ListEntityToDto(detailFactures1));
        });
        return factureDtoList;
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepo.getListFactureByActeur(acteurServ.getUserConnected().getId()).orElseThrow(() -> new NotExisteException("Facture n'existe pas "));
    }

    @Override
    public String getNumeroFacture(Long acteurId) {
        String numroFacture = String.valueOf(factureRepo.getnumbreFacture(acteurServ.getUserConnected().getId()));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return year + "N" + numroFacture;
    }

    @Override
    public void deletFacture(Long factureId) {
        Facture facture = factureRepo.findById(factureId).orElseThrow(() -> new NotExisteException("Facture n'existe pas"));
        Set<DetailFacture> detailFactureList = detailFactureRepo.getAllByFactureID(factureId).orElseThrow(() -> new NotExisteException("Details factures non existe"));
        detailFactureRepo.deleteAllById(detailFactureList.stream().map(d -> d.getId()).collect(Collectors.toList()));
        factureRepo.delete(facture);

    }

    @Override
    public ByteArrayInputStream exportFactureEmploy(Long factureId) throws FileNotFoundException, JRException {
        Facture facture = factureRepo.findById(factureId).orElseThrow(() -> new NotExisteException("Facture n'existe pas"));
        List<DetailFacture> detailFactureList = new ArrayList<>(detailFactureRepo.getAllByFactureID(factureId).get());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes;
        File file = ResourceUtils.getFile("classpath:jasperFiles/factureVenteTemp.jrxml");
        InputStream input = new FileInputStream(file);
        // JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detailFactureList);
        // add parametres to pdf file
        Map<String, Object> paramateres = new HashMap<>();
        getParams(paramateres,facture ,dataSource);
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/chakerJeux.PNG"));
            paramateres.put("efacture-logo", image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramateres, new JREmptyDataSource());
        // JasperExportManager.exportReportToPdfFile(jasperPrint ,"src/main/resources/FactureEmployees.pdf"); // upload file localy in specific path
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream); // tronsform our pdf file to outputStrem
        bytes = outputStream.toByteArray(); // tronsform our pdf outputStream to byte[]
        return new ByteArrayInputStream(bytes); // return pdf fil in array input stream

    }

    // tronsform arrayInput stream to page web
    @Override
    public void writePdfStreamToHttpServletResponse(HttpServletResponse response, ByteArrayInputStream byteArrayInputStream) throws Exception {
        OutputStream os = response.getOutputStream();
//        response.setHeader("content-disposition", "inline; filename=file.pdf");
        response.setContentType("application/pdf; name=\"MyFile.pdf\"");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Cache-Control", "private, must-revalidate, post-check=0, pre-check=0, max-age=1");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=\"file_from_server.pdf\"");
        byte[] pdfAsStream = new byte[byteArrayInputStream.available()];
        byteArrayInputStream.read(pdfAsStream);
        os.write(pdfAsStream);
        os.close();
    }
    void getParams(Map<String, Object> paramateres,Facture facture,JRBeanCollectionDataSource dataSource){

        paramateres.put("firstName", facture.getClient().getNom());
        paramateres.put("phone", facture.getClient().getNumeroTel());
        paramateres.put("lastNameClient", facture.getClient().getPrenom());
        paramateres.put("statusFacture", facture.getStatusFacture().toString());
        paramateres.put("statusPaiementFacture", facture.getStatusPaiementFacture().toString());
        paramateres.put("numeroFacture", facture.getNumero());
        paramateres.put("dateFacture", java.sql.Date.valueOf(facture.getDateFacture()));
        paramateres.put("prixTotal", facture.getPrixTotale());
        paramateres.put("collectionBeanParam", dataSource);
        paramateres.put("clientId", facture.getClient().getId());
        paramateres.put("adressClient", facture.getClient().getAdress());
    }

}
