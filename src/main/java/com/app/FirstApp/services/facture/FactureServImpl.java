package com.app.FirstApp.services.facture;

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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ProduitRepo produitRepo;
    @Autowired
    private FactureMapperService factureMapperService;

    @Autowired
    DetailFactureMapperService detailFactureMapperService;

    @Override
    public Optional<Facture> saveFacture(Facture facture) {
        Acteur acteur = acteurServ.getUserConnected();
        facture.setNumero(getNumeroFacture(acteur.getId()));
        facture.setActeur(acteur);
        Facture facture1 = factureRepo.save(facture);

        return Optional.of(facture1);
    }

    @Override
    public FactureDto saveFactureDto(FactureDto factureDto) {
        Acteur acteur = acteurServ.getUserConnected();

        String mes = "";
        List<DetailFacture> detailFacturesList = new ArrayList<>();
        List<Produits> produitsListUpdatedQuantite = new ArrayList<>();
        List<DetailFactureDto> detailFacturesDto = factureDto.getDetailFactures();
        factureDto.setNumero(getNumeroFacture(acteur.getId()));
        Facture facture = factureMapperService.dtoFactureToEntity(factureDto);
        facture.setActeur(acteurServ.getUserConnected());

        Facture factureSaved = factureRepo.save(facture);

        detailFacturesDto.forEach(fctDto -> {
            DetailFacture detailFacture = new DetailFacture();
            BigDecimal quantiteFacture = (fctDto.getProduits().getQuantite().subtract(fctDto.getQuantite()));
            if (quantiteFacture.compareTo(BigDecimal.ZERO)==0 || quantiteFacture.compareTo(BigDecimal.ZERO)==1) {
                fctDto.getProduits().setQuantite(quantiteFacture);
                produitsListUpdatedQuantite.add(fctDto.getProduits());
                detailFacture.setProduits(fctDto.getProduits());
                detailFacture.setQuantite(fctDto.getQuantite());
                detailFacture.setPrix(fctDto.getPrix());
                detailFacture.setFacture(factureSaved);
                detailFacturesList.add(detailFacture);
            }else{

                System.out.println("quantite insufffisante ");
            }


        });
        detailFactureRepo.saveAll(detailFacturesList);
        produitRepo.saveAll(produitsListUpdatedQuantite);







        return factureDto;
    }

    @Override
    public List<FactureDto> getListFactureDto() {
        Acteur acteur=acteurServ.getUserConnected();
        List<Facture>  factureList=factureRepo.getListFactureByActeur(acteur.getId());
        List<Long> idsFacture=factureList.stream().map(f -> f.getId()).collect(Collectors.toList());
        List<DetailFacture> detailFactures = detailFactureRepo.getAllByListIdsFacture(idsFacture).get();

        List<FactureDto> factureDtoList=factureMapperService.listEntityFactureToDto(factureList);
        factureDtoList.forEach(facDto -> {
          List<DetailFacture> detailFactures1=  detailFactures.stream().filter( df -> df.getFacture().getId().equals(facDto.getId())).collect(Collectors.toList());
            facDto.setDetailFactures(detailFactureMapperService.ListEntityToDto(detailFactures1));
        });
        return factureDtoList;
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepo.getListFactureByActeur(acteurServ.getUserConnected().getId());
    }

    @Override
    public String getNumeroFacture(Long acteurId) {
        String numroFacture = String.valueOf(factureRepo.getnumbreFacture(acteurServ.getUserConnected().getId()));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return year + "/" + numroFacture;
    }

    @Override
    public void deletFacture(Long factureId) {
      Facture facture = factureRepo.findById(factureId).get();
      Set<DetailFacture> detailFactureList= detailFactureRepo.getAllByFactureID(factureId).get();
      detailFactureRepo.deleteAllById(detailFactureList.stream().map(d ->d.getId()).collect(Collectors.toList()));
      factureRepo.delete(facture);

    }
}
