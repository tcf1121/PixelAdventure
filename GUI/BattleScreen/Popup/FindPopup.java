package GUI.BattleScreen.Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Back.Server.MySocketClient;
import GUI.BattleScreen.BattleForm;
import GUI.BattleScreen.RoomForm;

import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class FindPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnok;
	private JButton btncancle;
	private JLabel lblbg;
	private JTextField tfroomnum;
	private String  roomnum;
	private RoomForm owner;
	private MySocketClient Socket;
	
	public FindPopup(RoomForm owner) throws ClassNotFoundException, IOException {
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    icon = new ImageIcon(FindPopup.class.getResource("/image/popupbg/Findbg.png"));

		this.owner = owner;
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
		
	    scrollPane = new JScrollPane(bg);
	    
	    
	    
	    bg.add(tfroomnum);
	    bg.add(btncancle);
	    bg.add(btnok);
	    bg.add(lblbg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {		
		lblbg = new JLabel();
	    lblbg.setIcon(icon);
	    lblbg.setBounds(0, 0, 296, 179);
		tfroomnum = new JTextField();
		tfroomnum.setBounds(75, 76, 146, 38);
        tfroomnum.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 25));
        tfroomnum.setForeground(new Color(0, 0, 0));
	    btncancle = new JButton();
	    btncancle.setBorderPainted(false);
	    btncancle.setContentAreaFilled(false);
	    btncancle.setBounds(164, 129, 87, 38);
	    btnok = new JButton();
	    btnok.setBorderPainted(false);
	    btnok.setContentAreaFilled(false);
	    btnok.setBounds(50, 129, 87, 38);
	}
	
    private void showFrame() {
        setSize(296, 179);//프레임의 크기
        ;
        setUndecorated(true);
        Point p = new Point();
        p.setLocation(owner.getLocation().getX()+
        		(owner.getSize().getWidth()-getSize().getWidth())/2,
        		owner.getLocation().getY()+
        		(owner.getSize().getHeight()-getSize().getHeight())/2);
        setLocation(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() {

        btncancle.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		try {
					Socket.deleteRoom(roomnum);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		dispose();
        	}
    	});
        btnok.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		try {
					if(Socket.CheckRoom(tfroomnum.getText())) {
						if(Socket.CheckFull(tfroomnum.getText())) {
							JOptionPane.showMessageDialog(
		                    		FindPopup.this,
		                            "꽉 찬 방입니다."
		                    );
						}
						else {
							Socket.EnterRoom(tfroomnum.getText(), owner.getId());
							JOptionPane.showMessageDialog(
		                    		FindPopup.this,
		                            "접속을 시작합니다."
		                    );
							owner.setVisible(false);
							BattleForm BF = new BattleForm(owner.getwait(), tfroomnum.getText(), 0);
							BF.start();
						}
	                    
	                    
					}
					else {
	                    JOptionPane.showMessageDialog(
	                    		FindPopup.this,
	                            "생성되지 않은 번호입니다.\n"
	                            + "다시 입력해주세요."
	                    );
					}
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		dispose();
        	}
    	});
    }
}
