package hello.toy.word.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class WordRepository {


    private final DataSource dataSource;

    public String getReplceWord() {
        String url="";
        String user="";
        String password="";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String QUERY = "";
        String result = "";
        try {
            DriverManager.getConnection(url, user, password);
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(QUERY);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                result = rs.getString("replaceword");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(pstmt != null)
                    pstmt.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }
}
