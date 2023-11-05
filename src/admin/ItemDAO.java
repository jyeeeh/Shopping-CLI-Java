package admin;

import java.sql.*;

public class ItemDAO {
    private static ItemDAO itemDAO;
    private Connection conn;

    public ItemDAO() {

    }

    public static ItemDAO getInstance() {
        if (itemDAO == null) {
            itemDAO = new ItemDAO();
        }
        return itemDAO;
    }

    public void setConnection(Connection con) {
        this.conn = con;
    }
    public boolean insertItem(Item item) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql =
                    "INSERT INTO item (category_id, item_name, size, purchase_cnt, remain, price, item_contents) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getCategoryId());
            pstmt.setString(2, item.getItemName());
            pstmt.setString(3, item.getSize());
            pstmt.setInt(4, 0);
            pstmt.setInt(5, item.getRemain());
            pstmt.setInt(6, item.getPrice());
            pstmt.setString(7, item.getContent());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("----------------상품등록 완료------------------");

            return true;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItemRemain(int itemId, int newRemain){
        try {
            String sql =
                    "Update item set remain = COALESCE(?, remain) " +
                            "WHERE item_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newRemain);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("상품수정완료");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("상품수정실패");
        return false;
    }
}