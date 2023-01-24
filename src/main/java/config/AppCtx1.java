package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Configuration;
import spring.*;

//assembler 클레스 처럼 필요한 객체를 생성하고 생성한 객체에 의존을 주입한다.
@Configuration
@ComponentScan(basePackages = {"spring"}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "spring\\..*Dao"))
//스캔 대상에서 제외하거나 포함하기

public class AppCtx1 {

//    @Bean
//    public MemberDao memberDao() {
//        return new MemberDao();
//    }

    //스프링 빈에 의존 하는 다른 빈을 자동으로 주입하고 싶을 때 사용.
    @Autowired(required = false)
    private MemberDBDao memberDao;

    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDao);
    }

    @Bean
    public ChangePasswordService changePwdSvc() {
        ChangePasswordService pwdSvc = new ChangePasswordService();
//        pwdSvc.setMemberDao(memberDao);
        return pwdSvc;
    }

    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

//    @Bean
//    public MemberListPrinter listPrinter() {
//        return new MemberListPrinter(memberDBDao(), memberPrinter());
//    }

    @Bean
    public MemberInfoPrinter infoPrinter() {
//        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        // Autowired로 의존성 자동 주입
//        infoPrinter.setMemberDao(memberDao);
//        infoPrinter.setPrinter(memberPrinter());
        return new MemberInfoPrinter();
    }

    @Bean
    public Client client() {
        Client client = new Client();
        client.setHost("host");
        return client;
    }
}