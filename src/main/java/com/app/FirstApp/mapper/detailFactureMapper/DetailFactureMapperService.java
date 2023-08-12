package com.app.FirstApp.mapper.detailFactureMapper;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.modele.facture.DetailFactureDto;
import com.app.FirstApp.modele.facture.FactureDto;

import java.util.List;

public interface DetailFactureMapperService {

    DetailFacture dtoFactureDtoToEntity(DetailFactureDto detailFactureDto);
    DetailFactureDto entityDetailFactureDtoToDto(DetailFacture detailFacture);
    List<DetailFacture> ListDtoToEntity(List<DetailFactureDto> detailFactureDtos);
    List<DetailFactureDto> ListEntityToDto(List<DetailFacture> detailFactures);
}
