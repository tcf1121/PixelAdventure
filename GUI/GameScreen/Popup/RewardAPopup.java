package GUI.GameScreen.Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import Back.Datarelated.Equip;
import Back.Datarelated.Skill;
import Back.Server.MySocketClient;
import GUI.GameScreen.GameForm;
import GUI.MainScreen.Popup.AbilityAPopup;


public class RewardAPopup extends JDialog implements ItemListener{
	private static final long serialVersionUID = 1L;
    private GameForm owner;
    JScrollPane scrollPane;
    ImageIcon icon;
	private Skill Skill;
	private JLabel lblSkill;
	private JButton btnSkillex;
	private Equip Equip;
	private JLabel lblEquip;
	private JButton btnEquipex;
	private JLabel lblGold;
	private JLabel lblGcount;
	private JButton btnSelect;
	private int gold;
	private String id;
	private MySocketClient Socket;
	
	public RewardAPopup(GameForm owner, String class_no, int floor) throws ClassNotFoundException, IOException {
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		this.owner = owner;
        id = owner.getId();
        icon = new ImageIcon(AbilityAPopup.class.getResource("/image/popupbg/srewardbg.png"));
        Equip = Socket.rewardEquip(floor);
        Skill = Socket.rewardSkill(class_no);
		Random r = new Random();
        gold = floor * (r.nextInt(4)+1);
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
        
        bg.add(lblEquip); 	bg.add(lblSkill);
        bg.add(lblGold);	bg.add(lblGcount);
        bg.add(btnEquipex);	bg.add(btnSkillex);
        
        bg.add(btnSelect);
        
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        addListeners();
        
        showFrame();
    }
	private void init() {
		Font font = new Font("SansSerif", 1, 20);
        Color WColor = new Color(255, 255, 255);
        ImageIcon skillicon = new ImageIcon(RewardAPopup.class.getResource("/image/skill/"+Skill.getId()+".png"));
		ImageIcon equipicon = new ImageIcon(RewardAPopup.class.getResource("/image/equipment/"+Equip.getId()+".png"));
		ImageIcon goldicon = new ImageIcon(RewardAPopup.class.getResource("/image/ui/gold.gif"));

		
		lblSkill = new JLabel(skillicon);
		lblSkill.setBounds(261, 105, 80, 120);
		btnSkillex = new JButton();
		btnSkillex.setBorderPainted(false);
		btnSkillex.setContentAreaFilled(false);
		btnSkillex.setBounds(357, 98, 15, 15);
		
		lblEquip = new JLabel(equipicon);
		lblEquip.setBounds(443, 105, 80, 120);
		btnEquipex = new JButton();
		btnEquipex.setBorderPainted(false);
		btnEquipex.setContentAreaFilled(false);
		btnEquipex.setBounds(536, 98, 15, 15);
		
		lblGold = new JLabel(goldicon);
		lblGold.setBounds(83, 100, 80, 120);
		
		lblGcount = new JLabel(String.valueOf(gold)+"G");
		lblGcount.setHorizontalAlignment(JLabel.CENTER);
		lblGcount.setFont(font);
		lblGcount.setForeground(WColor);
		lblGcount.setBounds(83, 188, 80, 20);
		
        btnSelect = new JButton();
        btnSelect.setBorderPainted(false);
        btnSelect.setContentAreaFilled(false);
        btnSelect.setBounds(255, 272, 90, 50);
    }
    
    private void showFrame() {
        setSize(600, 347);//프레임의 크기
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
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	owner.gold += gold;
            	try {
					Socket.addUskill(id, Skill.getId());
					Socket.Equipped(id, Equip.getId());
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	JOptionPane.showMessageDialog(
                		RewardAPopup.this,
                        "미믹을 처지해 모든 보상을 얻습니다."
                );
            }
        });
    	btnSkillex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        RewardAPopup.this,
                        " "+ Skill.getName() + "\n" +
                        "마나 비용 :" + Skill.getUmana()+ "\n" +
                        "설명 :" + Skill.getExplan() + "\n",
                        "스킬 정보",JOptionPane.PLAIN_MESSAGE
                        );             
            }
    	});
    	btnEquipex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        RewardAPopup.this,
                        " "+ Equip.getName() + "\n" +
                        "희귀도 :" + Equip.getRarity()+ "\n" +
                        "설명 :" + Equip.getExplan()+ "\n" +
                        "효과 :" + Equip.getEf_ex() + "\n",
                        "장비설명",JOptionPane.PLAIN_MESSAGE
                        );             
            }
    	});

    }
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}
