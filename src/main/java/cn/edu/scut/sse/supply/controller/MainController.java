package cn.edu.scut.sse.supply.controller;

import cn.edu.scut.sse.supply.pojo.vo.KeyPairVO;
import cn.edu.scut.sse.supply.pojo.vo.ResponseResult;
import cn.edu.scut.sse.supply.service.MainService;
import cn.edu.scut.sse.supply.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.security.KeyPair;

/**
 * @author Yukino Yukinoshita
 */

@Controller
public class MainController {

    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = "/test")
    public @ResponseBody
    String test() {
        return "test success";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test-post")
    public @ResponseBody
    String testPost(@RequestParam String param) {
        return param;
    }

    @RequestMapping(value = "/helloworld/get")
    public @ResponseBody
    String get() {
        try {
            return mainService.get();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/get/blocknumber")
    public @ResponseBody
    String getBlockNumber() {
        try {
            return mainService.getBlockNumber();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/helloworld/set")
    public @ResponseBody
    String set(@RequestParam String val) {
        try {
            return mainService.set(val);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/key/generate")
    public @ResponseBody
    ResponseResult generateKeyPair() {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        KeyPairVO vo = new KeyPairVO();
        vo.setPrivateKey(RSAUtil.convertPrivateKey(keyPair.getPrivate()));
        vo.setPublicKey(RSAUtil.convertPublicKey(keyPair.getPublic()));
        return new ResponseResult().setCode(0).setMsg("生成成功").setData(vo);
    }

    @RequestMapping("/test/path")
    public @ResponseBody
    ResponseResult testPath(@RequestParam String path) {
        File file = new File(path);
        return new ResponseResult().setCode(0).setMsg(file.exists() + "-" + file.getAbsolutePath());
    }

}
