package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import spring.*;


@Configuration
public class AppCtx {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/spring?serverTimezone=UTC");
        ds.setUsername("spring");
        ds.setPassword("springpw");
        ds.setInitialSize(2); // 커넥션 풀을 초기화 할때 생성할 초기 커넥션 갯수
        ds.setMaxActive(10); // 커넥션 풀에서 가져 올 수 있는 최대 커넥션 갯수
        // 커넥션 풀을 이용하면 매번 새로운 커넥션 연결을 할 때보다 성능이 좋다, 재사용성도 좋다
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(60000 * 3);
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
        return ds;
    }

    @Bean
    public MemberDBDao memberDBDao() {
        return new MemberDBDao(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDBDao());
    }

    @Bean
    public ChangePasswordService changePwdSvc() {
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDBDao());
        return pwdSvc;
    }

    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter() {
        return new MemberListPrinter(memberDBDao(), memberPrinter());
    }

    @Bean
    public MemberInfoPrinter infoPrinter() {
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDBDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }
}
