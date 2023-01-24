package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.List;


@Component
public class MemberDBDao {

    private JdbcTemplate jdbcTemplate;

    public MemberDBDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public MemberDBDao() {

    }


    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query("select * from MEMBER ", (ResultSet rs, int rowNum) -> {
            Member member = new Member(
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD"),
                    rs.getString("NAME"),
                    rs.getTimestamp("REGDATE").toLocalDateTime());
            member.setId(rs.getLong("ID"));
            return member;
        });

        return results;
    }

    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                new RowMapper<Member>() { //ResultSet을 Member 객체로 변환 //todo 람다로 표현해보기
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE").toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                }, email);
        return results.isEmpty() ? null : results.get(0);
    }

    public int count() {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from MEMBER", Integer.class);
        return count;
    }

    public void insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE)" +
                            "values (?, ?, ?, ?)",
                    new String[] {"id"});
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
            return pstmt;
        }, keyHolder);

        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }


    public void update(Member member) {
        jdbcTemplate.update("update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail());
    }

}
