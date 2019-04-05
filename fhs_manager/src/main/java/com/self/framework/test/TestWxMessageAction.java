package com.self.framework.test;

import com.alibaba.fastjson.JSON;
import com.self.framework.utils.StrTool;
import com.self.framework.wx.WxTools;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/test_wx_msg")
public class TestWxMessageAction {

    @Autowired
    private WxTools wxTools;

    @RequestMapping(value = "/t", method = {RequestMethod.GET, RequestMethod.POST})
    public void msg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (!wxTools.getWxMpService().checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }

        String echostr = request.getParameter("echostr");
        if (!StrTool.is_empty(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(echostr);
            return;
        }

        WxMpService wxMpService = wxTools.getWxMpService();
        String msgSignature = request.getParameter("msg_signature");
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
        //WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());

        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        router.rule().async(false).handler(new TestWxMessageHandler()).end();
        WxMpXmlOutMessage outMessage = router.route(inMessage);
        String s = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        System.out.println(s);
        response.getWriter().write(s.replaceAll(" ",""));
//        response.getWriter().write(outMessage.toXml().replaceAll(" ",""));
    }

}
