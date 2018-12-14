
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class table extends JFrame {
    private JPanel contentPane;

    //DB property
    private String query = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    //Models
    private GameTableModel gameModel;
    private MemberTableModel memberModel;
    private RentalTableModel rentalModel;
    /**
     * Launch the application.
     */
    /**
     * Create the frame.
     */

    public table() {
        this.setTitle("App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 666, 486);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        this.setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 36, 620, 394);
        contentPane.add(tabbedPane);


        gameModel = new GameTableModel(getGame());
        memberModel = new MemberTableModel(getMember());
        rentalModel = new RentalTableModel(getRental());

        //Game Tab
        gamepanel game = new gamepanel(gameModel);
        game.CreatePanel();
        tabbedPane.addTab("Game", null, game.getPanel(), "Display Game List");

        //Member Tab
        memberpanel member = new memberpanel(memberModel);
        member.CreatePanel();
        tabbedPane.addTab("Member", null, member.getPanel(), null);

        //Borrow Tab
        rentalpanel rent = new rentalpanel(memberModel,gameModel,rentalModel);
        rent.CreatePanel();
        tabbedPane.addTab("Rent", null, rent.getPanel(), null);

        //Return Tab
        returnpanel returns = new returnpanel(rentalModel,gameModel);
        returns.CreatePanel();
        tabbedPane.addTab("Return", null, returns.getPanel(), null);
    }


    public List getGame(){
        List gameList = new ArrayList<Game>();
        try{
            con = database.connect();
            query = "select * from game order by gameid";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                gameList.add(new Game(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            database.close(con,ps,rs);
        }

        return gameList;
    }

    public List getMember(){
        List memberList = new ArrayList<Member>();
        try{
            con = database.connect();
            query = "select * from members ORDER BY memberid";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                memberList.add(new Member(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            database.close(con,ps,rs);
        }
        return memberList;
    }

    public List getRental()
    {
        List rentalList = new ArrayList<Rental>();
        try{
            con = database.connect();
            query ="select * from borrow order by gameid";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                rentalList.add(new Rental(Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2)),rs.getDate(3),rs.getDate(4),rs.getDate(5)));
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null , e);
        }
        finally {
            database.close(con,ps,rs);
        }
        return rentalList;
    }



}