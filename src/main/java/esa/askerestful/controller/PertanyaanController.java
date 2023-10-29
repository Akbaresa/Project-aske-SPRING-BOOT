package esa.askerestful.controller;

import esa.askerestful.entity.User;
import esa.askerestful.model.CreatePertanyaanrReq;
import esa.askerestful.model.PertanyaanResponse;
import esa.askerestful.model.WebResponse;
import esa.askerestful.service.PertanyaanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PertanyaanController {

    @Autowired
    private PertanyaanService pertanyaanService;

    @PostMapping(
            path = "/api/pertanyaan",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PertanyaanResponse> create(User user , @RequestBody CreatePertanyaanrReq  req){
        PertanyaanResponse pertanyaanResponse = pertanyaanService.create(user , req);

        return WebResponse.<PertanyaanResponse>builder()
                .data(pertanyaanResponse)
                .build();
    }

    @GetMapping(
            path = "/api/pertanyaan/{idPertanyaan}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PertanyaanResponse> get(User user ,@PathVariable("idPertanyaan") String id){
        PertanyaanResponse pertanyaanResponse = pertanyaanService.get(user , id);

        return WebResponse.<PertanyaanResponse>builder()
                .data(pertanyaanResponse)
                .build();
    }

}