package com.app.FirstApp.mapper.factureMapper;

import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.modele.facture.FactureDto;

import java.util.List;

public interface FactureMapperService {
    Facture dtoFactureToEntity(FactureDto factureDto);
    FactureDto entityFactureToDto(Facture facture);
    List<FactureDto> listEntityFactureToDto(List<Facture> factures);
    List<Facture> listDtoFactureToEntity(List<FactureDto> factureDtoList);
}
