package com.app.FirstApp.mapper.detailFactureMapper;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.modele.facture.DetailFactureDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DetailFactureMapper implements DetailFactureMapperService {
    @Override
    public DetailFacture dtoFactureDtoToEntity(DetailFactureDto detailFactureDto) {
        DetailFacture detailFacture = new DetailFacture();
        detailFacture.setId(detailFactureDto.getId());
        detailFacture.setQuantite(detailFactureDto.getQuantite());
        detailFacture.setPrix(detailFactureDto.getPrix());
        detailFacture.setProduits(detailFactureDto.getProduits());
        detailFacture.setLibelleProduit(detailFactureDto.getLibelleProduit());
        detailFacture.setCodeProduit(detailFactureDto.getCodeProduit());
        return detailFacture;
    }

    @Override
    public DetailFactureDto entityDetailFactureDtoToDto(DetailFacture detailFacture) {
        DetailFactureDto detailFactureDto = new DetailFactureDto();
        detailFactureDto.setId(detailFacture.getId());
        detailFactureDto.setQuantite(detailFacture.getQuantite());
        detailFactureDto.setPrix(detailFacture.getPrix());
        detailFactureDto.setProduits(detailFacture.getProduits());
        detailFactureDto.setLibelleProduit(detailFacture.getLibelleProduit());
        detailFactureDto.setCodeProduit(detailFacture.getCodeProduit());
        return detailFactureDto;
    }

    @Override
    public List<DetailFacture> ListDtoToEntity(List<DetailFactureDto> detailFactureDtos) {
        List<DetailFacture> factureList = new ArrayList<>();
        detailFactureDtos.forEach(dtF -> factureList.add(dtoFactureDtoToEntity(dtF)));
        return factureList;
    }

    @Override
    public List<DetailFactureDto> ListEntityToDto(List<DetailFacture> detailFactures) {
        List<DetailFactureDto> factureDtoList = new ArrayList<>();
        detailFactures.forEach(dt -> factureDtoList.add(entityDetailFactureDtoToDto(dt)));
        return factureDtoList;
    }
}
