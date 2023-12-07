package GUI.Manager;

import javax.swing.*;

import Back.Server.MasterServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ManagerForm extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private SeverLoginForm owner;
    private JButton btnaccount;
    //private JButton btnchecklog;
    private JButton btnlogout;
    public JTextArea tfuserlist=  new JTextArea();;
    MasterServer msvr = new MasterServer();
    public ManagerForm(SeverLoginForm owner) {
    	this.owner = owner;
        icon = new ImageIcon(ManagerForm.class.getResource("/image/background/managerbg.png"));
        
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        
        bg.setLayout(null);
        init();
        bg.add(btnaccount);
        //bg.add(btnchecklog);
        bg.add(btnlogout);
        
        
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(ManagerForm.class.getResource("/image/background/managerbg.png")));
        lblNewLabel.setBounds(0, 0, 786, 563);
        
        
        bg.add(tfuserlist);
        bg.add(lblNewLabel);
        
        
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);

        
        addListeners();
        showFrame();
        
    	msvr.setGUI(this);
    	msvr.start();
    }
    public void init() {
    	Font font = new Font("나눔고딕 ExtraBold", Font.BOLD, 20);
        
        btnaccount = new JButton();
		btnaccount.setBorderPainted(false);
		btnaccount.setContentAreaFilled(false);
        btnaccount.setBounds(133, 168, 114, 114);
        
        /*btnchecklog = new JButton();
		btnchecklog.setBorderPainted(false);
		btnchecklog.setContentAreaFilled(false);
        btnchecklog.setBounds(133, 329, 114, 114);
        */
        btnlogout = new JButton();
		btnlogout.setBorderPainted(false);
		btnlogout.setContentAreaFilled(false);
        btnlogout.setBounds(605, 43, 125, 38);
        
        tfuserlist.setFont(font);
        tfuserlist.setEditable(false);
        tfuserlist.setBounds(414, 151, 314, 355);
        
    }
    public void showFrame() {
        setTitle("픽셀 어드벤쳐 메인 서버 관리");
        setSize(800,600);
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫았을 때 메모리에서 제거되도록 설정합니다.
        setResizable(false);//창 크기 고정
        setVisible(true);
    }

    public void addListeners() {

        btnaccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new AccountForm(ManagerForm.this);
            }
        });
        /*btnchecklog.addActionListener(new ActionListener() {
            @Override           
            public void actionPerformed(ActionEvent e) {
            	
                
            }
        });*/
        btnlogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		ManagerForm.this,
                        "관리자 모드를 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	dispose();
                	owner.setVisible(true);
                }
            	
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		ManagerForm.this,
                        "종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
	public void AppendUserList(ArrayList<String> id) {
		String name;
		for (int i = 0; i < id.size(); i++) {
			name = (String) id.get(i);
			tfuserlist.append(name + "\n");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
