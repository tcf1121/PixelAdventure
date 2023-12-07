package GUI.MainScreen.Popup;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GUI.MainScreen.WaitForm;
import Back.Server.MySocketClient;
import Back.Datarelated.Ability;


public class AbilityAPopup extends JDialog{
	private static final long serialVersionUID = 1L;
    private WaitForm owner;
    JScrollPane scrollPane;
    ImageIcon icon;

    private JButton btnStrP;
    private JButton btnStrM;
    private JButton btnDefP;
    private JButton btnDefM;
    private JButton btnHpP;
    private JButton btnHpM;
    private JButton btnMpP;
    private JButton btnMpM;
 /*   private JButton btnCriP;
    private JButton btnCriM;
    private JButton btnMissP;
    private JButton btnMissM;*/
    private JButton btnApply;
    private JButton btnCancel;
    private JLabel lblStr;
    private JLabel lblDef;
    private JLabel lblHp;
    private JLabel lblMp;
    private JLabel lblAP;
    private Ability sAP;
    int Strcount = 0;
    int Defcount = 0;
    int Hpcount = 0;
    int Mpcount = 0;
    int Cricount = 0;
    int Misscount = 0;
    int APcount;
    private MySocketClient Socket;
    
	public AbilityAPopup(WaitForm owner, int Lv) throws ClassNotFoundException, IOException {
        this.owner = owner;
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        APcount = Lv;
    	if(Socket.isSaveAP(owner.getId())) {
    		sAP = Socket.LoadAP(owner.getId());
    		Strcount = sAP.getStr();
    		Defcount = sAP.getDef();
    		Hpcount = sAP.getHp();
    		Mpcount = sAP.getMp();
    		/*Cricount = sAP.getCri();
    		Misscount = sAP.getMiss();*/
    		APcount = (Lv - ((Strcount+Defcount)+
    				(Hpcount+Mpcount+Cricount+Misscount)/2));
    	}
        icon = new ImageIcon(AbilityAPopup.class.getResource("/image/popupbg/abilityAbg.png"));
       
        //��� Panel ������ �������������� ����      
        JPanel bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //�׸��� ǥ���ϰ� ����,�����ϰ� ����
                super.paintComponent(g);
            }
        };
       
        bg.setLayout(null);
        init();
        bg.add(btnStrP); bg.add(btnStrM); bg.add(btnDefP);
        bg.add(btnDefM); bg.add(btnHpP); bg.add(btnHpM);
        bg.add(btnMpP); bg.add(btnMpM); //bg.add(btnCriP);
        //bg.add(btnCriM); bg.add(btnMissP); bg.add(btnMissM);
        bg.add(btnApply); bg.add(btnCancel);
        bg.add(lblStr); bg.add(lblDef); bg.add(lblHp);
        bg.add(lblMp); //bg.add(lblCri); bg.add(lblMiss);
        bg.add(lblAP);
        
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        addListeners();
        
        showFrame();
    }
	private void init() {
        // ������ ����
		Font font = new Font("SansSerif", 1, 25);
        Color WColor = new Color(255, 255, 255);
        int ws = 27;
        
        btnStrP = new JButton();
        btnStrP.setBorderPainted(false);
        btnStrP.setContentAreaFilled(false);
        btnStrP.setBounds(220, 185, ws, ws);
        btnStrM = new JButton();
        btnStrM.setBorderPainted(false);
        btnStrM.setContentAreaFilled(false);
        btnStrM.setBounds(220, 215, ws, ws);
        btnDefP = new JButton();
        btnDefP.setBorderPainted(false);
        btnDefP.setContentAreaFilled(false);
        btnDefP.setBounds(510, 185, ws, ws);
        btnDefM = new JButton();
        btnDefM.setBorderPainted(false);
        btnDefM.setContentAreaFilled(false);
        btnDefM.setBounds(510, 215, ws, ws);
        btnHpP = new JButton();
        btnHpP.setBorderPainted(false);
        btnHpP.setContentAreaFilled(false);
        btnHpP.setBounds(220, 305, ws, ws);
        btnHpM = new JButton();
        btnHpM.setBorderPainted(false);
        btnHpM.setContentAreaFilled(false);
        btnHpM.setBounds(220, 335, ws, ws);
        btnMpP = new JButton();
        btnMpP.setBorderPainted(false);
        btnMpP.setContentAreaFilled(false);
        btnMpP.setBounds(510, 305, ws, ws);
        btnMpM = new JButton();
        btnMpM.setBorderPainted(false);
        btnMpM.setContentAreaFilled(false);
        btnMpM.setBounds(510, 335, ws, ws);
        /*btnCriP = new JButton("+");
        btnCriP.setBackground(YColor);
        btnCriP.setForeground(BColor);
        btnCriP.setBounds(250, 350, ws, ws);
        btnCriM = new JButton("-");
        btnCriM.setBackground(YColor);
        btnCriM.setForeground(BColor);
        btnCriM.setBounds(310, 350, ws, ws);
        btnMissP = new JButton("+");
        btnMissP.setBackground(YColor);
        btnMissP.setForeground(BColor);
        btnMissP.setBounds(570, 350, ws, ws);
        btnMissM = new JButton("-");
        btnMissM.setBackground(YColor);
        btnMissM.setForeground(BColor);
        btnMissM.setBounds(630, 350, ws, ws);*/
        btnApply = new JButton();
        btnApply.setBorderPainted(false);
        btnApply.setContentAreaFilled(false);
        btnCancel = new JButton();
        btnCancel.setBorderPainted(false);
        btnCancel.setContentAreaFilled(false);
        btnApply.setBounds(150, 410, 120, 65);
        btnCancel.setBounds(300, 410, 120, 65);
        
        lblStr = new JLabel(""+Strcount);
        lblStr.setBounds(155, 190, ws, ws+5);
        lblStr.setFont(font);
        lblStr.setForeground(WColor);
        lblDef = new JLabel(""+Defcount);
        lblDef.setBounds(445, 190, ws, ws+5);
        lblDef.setFont(font);
        lblDef.setForeground(WColor);
        lblHp = new JLabel(""+Hpcount);
        lblHp.setBounds(155, 310, ws, ws+5);
        lblHp.setFont(font);
        lblHp.setForeground(WColor);
        lblMp = new JLabel(""+Mpcount);
        lblMp.setBounds(445, 310, ws, ws+5);
        lblMp.setFont(font);
        lblMp.setForeground(WColor);
        /*lblCri = new JLabel(""+Cricount);
        lblCri.setBounds(195, 350, ws, ws);
        lblCri.setFont(new Font("����", Font.BOLD, 35));
        lblCri.setForeground(WColor);
        lblMiss = new JLabel(""+Misscount);
        lblMiss.setBounds(515, 350, ws, ws);
        lblMiss.setFont(new Font("����", Font.BOLD, 35));
        lblMiss.setForeground(WColor);*/
        lblAP = new JLabel(""+APcount);
        lblAP.setBounds(280, 90, ws, ws+5);
        lblAP.setFont(font);
        lblAP.setForeground(WColor);
    }
	
    private void showFrame() {
        setSize(570,509);//프레임 크기
        setUndecorated(true);
        Point p = new Point();
        p.setLocation(owner.getLocation().getX()+
        		(owner.getSize().getWidth()-getSize().getWidth())/2,
        		owner.getLocation().getY()+
        		(owner.getSize().getHeight()-getSize().getHeight())/2);
        setLocation(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//프레임 변경 x
        setVisible(true);
    }
	
    private void addListeners() {
    	
        btnStrP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Strcount==10 || APcount == 0) {}
            	else{
            		Strcount++;
            		lblStr.setText(""+Strcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnStrM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Strcount==0) {}
            	else{
            		Strcount--;
            		lblStr.setText(""+Strcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnDefP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Defcount==10|| APcount == 0) {}
            	else{
            		Defcount++;
            		lblDef.setText(""+Defcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnDefM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Defcount==0) {}
            	else{
            		Defcount--;
            		lblDef.setText(""+Defcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnHpP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Hpcount==20|| APcount == 0) {}
            	else{
            		Hpcount = Hpcount +2;
            		lblHp.setText(""+Hpcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnHpM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Hpcount==0) {}
            	else{
            		Hpcount = Hpcount -2;
            		lblHp.setText(""+Hpcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMpP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Mpcount==20|| APcount == 0) {}
            	else{
            		Mpcount = Mpcount +2;
            		lblMp.setText(""+Mpcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMpM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Mpcount==0) {}
            	else{
            		Mpcount = Mpcount -2;
            		lblMp.setText(""+Mpcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        /*btnCriP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Cricount==10|| APcount == 0) {}
            	else{
            		Cricount = Cricount +2;
            		lblCri.setText(""+Cricount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnCriM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Cricount==0) {}
            	else{
            		Cricount = Cricount -2;
            		lblCri.setText(""+Cricount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMissP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Misscount==10|| APcount == 0) {}
            	else{
            		Misscount = Misscount +2;
            		lblMiss.setText(""+Misscount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMissM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Misscount==0) {}
            	else{
            		Misscount = Misscount -2;
            		lblMiss.setText(""+Misscount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });*/
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	sAP = new Ability(getName(), Strcount,
            			Defcount, Hpcount, Mpcount);
            	try {
					Socket.SaveAP(owner.getId(), sAP);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                dispose();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
    }

}
