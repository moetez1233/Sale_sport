package com.app.FirstApp.mapper.factureMapper;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.mapper.detailFactureMapper.DetailFactureMapperService;
import com.app.FirstApp.modele.facture.DetailFactureDto;
import com.app.FirstApp.modele.facture.FactureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FactureMapper implements FactureMapperService {
    @Autowired
    private DetailFactureMapperService detailFactureMapperService;
    @Override
    public Facture dtoFactureToEntity(FactureDto factureDto) {
        Facture facture = new Facture();
        facture.setId(factureDto.getId());
        facture.setNumero(factureDto.getNumero());
        facture.setStatusFacture(factureDto.getStatusFacture());
        facture.setStatusPaiementFacture(factureDto.getStatusPaiementFacture());
        facture.setPrixTotale(factureDto.getPrixTotale());
        facture.setClient(factureDto.getClient());
        facture.setDateFacture(factureDto.getDateFacture());
        return facture;
    }

    @Override
    public FactureDto entityFactureToDto(Facture facture) {
        FactureDto factureDto = new FactureDto();
        factureDto.setId(facture.getId());
        factureDto.setNumero(facture.getNumero());
        factureDto.setStatusFacture(facture.getStatusFacture());
        factureDto.setStatusPaiementFacture(facture.getStatusPaiementFacture());
        factureDto.setPrixTotale(facture.getPrixTotale());
        factureDto.setClient(facture.getClient());
        factureDto.setDateFacture(facture.getDateFacture());
        return factureDto;
    }

    @Override
    public List<FactureDto> listEntityFactureToDto(List<Facture> factures) {
        List<FactureDto> factureDtos=new ArrayList<>();
        factures.forEach(f -> {
            factureDtos.add(entityFactureToDto(f));
        });
        return factureDtos;
    }

    @Override
    public List<Facture> listDtoFactureToEntity(List<FactureDto> factureDtoList) {
        List<Facture> factureList = new ArrayList<>();
        factureDtoList.forEach(fdto -> {
            factureList.add(dtoFactureToEntity(fdto));
        });
        return factureList;
    }
}
