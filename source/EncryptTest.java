package com.zhisou.ac.b2c.shop.service.test;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import java.security.Key;

import org.junit.Assert;
import org.junit.Test;

public class EncryptTest {

  @Test
  public void encrypt() {
      AesCipherService aesCipherService = new AesCipherService();
      aesCipherService.setKeySize(128); //设置key长度
      //生成key
      Key key = aesCipherService.generateNewKey();
      StringBuffer sb = new StringBuffer();
      for (byte b : key.getEncoded()) {
          sb.append(b).append(",");
      }
      System.out.println(sb.toString());
      String text = "hello";
      //加密
      String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
      //解密
      String text2 =
       new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

      Assert.assertEquals(text, text2);
  }
    /**
     * 上传文件流
     * @author GuoJun
     * @param  request            http请求参数
     * @param  paraName           文件流参数名
     * @param  pathPrefix         文件保存前缀
     * @return ResponseData
     */
    protected ResponseData uploadFile(HttpServletRequest request, String paraName, String pathPrefix) {
        try {
            // 获取上传文件
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multipartRequest.getFiles(paraName);
            // MultipartFile multipartFile = multipartRequest.getFile("img");

            if (fileList == null || fileList.size() == 0)
                return new ResponseData(false, "文件不能为空");
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (MultipartFile multipartFile : fileList) {
                // 文件全名
                String realFileName = multipartFile.getOriginalFilename();

                // 文件后缀
                String suffix = realFileName.substring(realFileName.lastIndexOf("."));

                // 获取不重复文件名
                String fileName = UUID.randomUUID().toString() + suffix;
                // 获取文件保存路径
                String filePath = fullPath(pathPrefix, fileName);
                byte[] fileByte = multipartFile.getBytes();
                FileUtils.writeByteArrayToFile(new File(filePath), fileByte);
                String src = fileName.charAt(0) + "/" + fileName;
                Map<String, String> map = new HashMap<String, String>();
                map.put("fileName", realFileName);
                map.put("src", src);
                map.put("filePath", filePath);
                list.add(map);
            }
            ResponseData response = new ResponseData(true);
            response.setObj(list);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.FAILED_NO_DATA;
        }
    }
    /**
     * 获得文件完整路径
     * @param pathPrefix
     * @param fileName
     * @return
     */
    private String fullPath(String pathPrefix, String fileName) {
        if (pathPrefix.endsWith("\\") || pathPrefix.endsWith("/")) {
            return pathPrefix + fileName.charAt(0) + File.separator + fileName;
        } else {
            return pathPrefix + File.separator + fileName.charAt(0) + File.separator + fileName;
        }
    }
}
