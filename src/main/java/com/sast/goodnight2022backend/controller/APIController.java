package com.sast.goodnight2022backend.controller;

import com.sast.goodnight2022backend.entity.Blessing;
import com.sast.goodnight2022backend.service.BlessingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author NuoTian
 * @date 2022/12/23
 */
@RestController
public class APIController {
    private BlessingService blessingService;

    @Autowired
    public void setBlessingService(BlessingService blessingService) {
        this.blessingService = blessingService;
    }

    @PostMapping("/getBlessing")
    public HashMap<String, Object> getBlessingController(@RequestParam("uid") String uid) {
        return blessingService.getBlessing(uid);
    }

    @PostMapping("/sendBlessing")
    public void sendBlessing(@RequestBody @Valid Blessing blessing) {
        blessingService.sendBlessing(blessing);
    }

    @PostMapping("/likeBlessing")
    public HashMap<String, Object> likeBlessing(@RequestParam("bid") Integer bid,
                               @RequestParam("uid") String uid) {
        return blessingService.likeBlessing(bid, uid);
    }

    @PostMapping("/getRandomBlessing")
    public HashMap<String, Object> getRandBlessing(@RequestParam("uid") String uid) {
        return blessingService.getRandBlessing(uid);
    }
}
