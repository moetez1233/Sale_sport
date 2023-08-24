package com.app.FirstApp.services.facture;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.modele.facture.FactureDto;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FactureService {
    FactureDto saveFactureDto(FactureDto factureDto);
    List<FactureDto> getListFactureDto();
    Optional<Facture> saveFacture(Facture facture);
    List<Facture> getAllFactures();
    String getNumeroFacture(Long acteurId);
    void deletFacture(Long factureId);
    ByteArrayInputStream exportFactureEmploy(Long factureId) throws FileNotFoundException, JRException;
    void writePdfStreamToHttpServletResponse(HttpServletResponse response, ByteArrayInputStream byteArrayInputStream) throws Exception;


}
