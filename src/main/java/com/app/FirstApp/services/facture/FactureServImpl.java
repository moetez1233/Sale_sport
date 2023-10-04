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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
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


    private static final Logger log = LoggerFactory.getLogger(FactureServImpl.class);


    @Override
    public void writePdfStreamToHttpServletResponse(HttpServletResponse response, ByteArrayInputStream byteArrayInputStream) throws Exception {
        OutputStream os = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=facture.pdf");
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        os.close();
        byteArrayInputStream.close();
    }

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
                throw new RuntimeException("Quantite produits insuffisante");
            }
        });
        detailFactureRepo.saveAll(detailFacturesList);
        produitRepo.saveAll(produitsListUpdatedQuantite);
        return factureDto;
    }

    @Override
    public List<FactureDto> getListFactureDto() {
        Acteur acteur = acteurServ.getUserConnected();
        List<Facture> factureList = factureRepo.getListFactureByActeur(acteur.getId()).orElseThrow(() -> new NotExisteException("Utilisateur"+ acteur.getEmail()+" n'existe pas"));
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
    public ByteArrayInputStream exportFactureEmploy(Long factureId) throws JRException, IOException {
        Facture facture = factureRepo.findById(factureId).orElseThrow(() -> new NotExisteException("facture : "+factureId+" n'existe pas"));
        List<DetailFacture> detailFactureList = new ArrayList<>(detailFactureRepo.getAllByFactureID(factureId).get());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes;
        InputStream input = this.getClass().getResourceAsStream("/jasperFiles/factureVenteTemp.jrxml");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detailFactureList);
        Map<String, Object> paramateres = new HashMap<>();
        getParams(paramateres, facture, dataSource);

        try {
            // Load the image using getResourceAsStream
            InputStream imageInputStream = this.getClass().getResourceAsStream("/chakerJeux.PNG");
            BufferedImage image = ImageIO.read(imageInputStream);
            paramateres.put("efacture-logo", image);
        } catch (Exception e) {
            log.error("Error loading image: " + e.getMessage());
        }

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramateres, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        bytes = outputStream.toByteArray();
        return new ByteArrayInputStream(bytes);
    }


    void getParams(Map<String, Object> paramateres, Facture facture, JRBeanCollectionDataSource dataSource){
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
        paramateres.put("adressClient", facture.getClient().getAdress());    }
}
