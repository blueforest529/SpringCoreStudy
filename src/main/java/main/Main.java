package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import spring.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
//        MemberDao memberDao = new MemberDao();
//        MemberRegisterService regSvc = new MemberRegisterService(memberDao);
//        ChangePasswordService pwdSvc = new ChangePasswordService();
//        pwdSvc.setMemberDao(memberDao);

        // 스프링 컨테이너 빈 라이프 사이클
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        Client client = ctx.getBean(Client.class);
        client.send();
        ctx.close();
    }
}
