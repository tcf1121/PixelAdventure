package GUI.MainScreen.Popup;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import GUI.MainScreen.WaitForm;
import Back.Datarelated.CharDataSet;


public class SelectPopup extends JDialog implements ItemListener{
	private static final long serialVersionUID = 1L;
	private WaitForm owner;
    JScrollPane scrollPane;
    ImageIcon icon;
	ImageIcon iiClass = new ImageIcon(SelectPopup.class.getResource("/image/ui/selectCK.png"));
	ImageIcon iiClassO = new ImageIcon(SelectPopup.class.getResource("/image/ui/selectCKO.png"));
	ImageIcon warrioricon = new ImageIcon(SelectPopup.class.getResource("/image/character/warrior_stand.gif"));
	ImageIcon wizardicon = new ImageIcon(SelectPopup.class.getResource("/image/character/wizard_stand.gif"));
	ImageIcon rougeicon = new ImageIcon(SelectPopup.class.getResource("/image/character/rouge_stand.gif"));
	ImageIcon warriorSicon = new ImageIcon(SelectPopup.class.getResource("/image/character/warrior_walk.gif"));
	ImageIcon wizardSicon = new ImageIcon(SelectPopup.class.getResource("/image/character/wizard_walk.gif"));
	ImageIcon rougeSicon = new ImageIcon(SelectPopup.class.getResource("/image/character/rouge_walk.gif"));
	
	private JRadioButton rbRouge;
	private JRadioButton rbWarrior;
	private JRadioButton rbwizard;
	private JLabel lblrogue;
	private JLabel lblwarrior;
	private JLabel lblwizard;
	private JButton btnSelect;
	private JButton btnCancle;
	private ButtonGroup bgClass;
	public SelectPopup(WaitForm owner) {
		this.owner = owner;
		new CharDataSet();
        icon = new ImageIcon(SelectPopup.class.getResource("/image/popupbg/SelectPopup.png"));
        
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
        bg.add(rbRouge);
        bg.add(rbWarrior);
        bg.add(rbwizard);
        bg.add(lblwarrior);
        bg.add(lblwizard);
        bg.add(lblrogue);
        bg.add(btnSelect);
        bg.add(btnCancle);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        addListeners();
        showFrame();
	}

	private void init()  {
		// 위치 설정

		rbRouge = new JRadioButton(iiClass);
		rbRouge.setBounds(155, 315, 25, 25);
		rbRouge.setBorderPainted(false);
		rbRouge.setContentAreaFilled(false);
		rbRouge.addItemListener(this);
		
		rbWarrior = new JRadioButton(iiClass);
		rbWarrior.setBounds(358, 315, 25, 25);
		rbWarrior.setBorderPainted(false);
		rbWarrior.setContentAreaFilled(false);
		rbWarrior.addItemListener(this);
		
		rbwizard = new JRadioButton(iiClass);
		rbwizard.setBounds(562, 315, 25, 25);
		rbwizard.setBorderPainted(false);
		rbwizard.setContentAreaFilled(false);
		rbwizard.addItemListener(this);
		
		bgClass = new ButtonGroup();
		bgClass.add(rbRouge);
		bgClass.add(rbWarrior);
		bgClass.add(rbwizard);
		
		lblrogue = new JLabel(rougeicon);
		lblrogue.setBounds(100, 100, 200, 200);
		lblwarrior = new JLabel(warrioricon);
		lblwarrior.setBounds(310, 100, 200, 200);
		lblwizard = new JLabel(wizardicon);
		lblwizard.setBounds(510, 100, 200, 200);

        btnSelect = new JButton();
        btnSelect.setBorderPainted(false);
        btnSelect.setContentAreaFilled(false);
        btnCancle = new JButton();
        btnCancle.setBorderPainted(false);
        btnCancle.setContentAreaFilled(false);
        btnSelect.setBounds(190, 380, 200, 65);
        btnCancle.setBounds(360, 380, 200, 65);
	}
	
    private void showFrame() {
        setSize(745,463);//프레임의 크기
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
	
    public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(rbRouge.isSelected()) {
				rbRouge.setIcon(iiClassO);
				rbWarrior.setIcon(iiClass);
				rbwizard.setIcon(iiClass);
				lblrogue.setIcon(rougeSicon);
				lblwarrior.setIcon(warrioricon);
				lblwizard.setIcon(wizardicon);
			}
			else if(rbWarrior.isSelected()) {			
				rbRouge.setIcon(iiClass);
				rbWarrior.setIcon(iiClassO);
				rbwizard.setIcon(iiClass);
				lblrogue.setIcon(rougeicon);
				lblwarrior.setIcon(warriorSicon);
				lblwizard.setIcon(wizardicon);
			}
			else if(rbwizard.isSelected()) {
				rbRouge.setIcon(iiClass);
				rbWarrior.setIcon(iiClass);
				rbwizard.setIcon(iiClassO);
				lblrogue.setIcon(rougeicon);
				lblwarrior.setIcon(warrioricon);
				lblwizard.setIcon(wizardSicon);
			}
		}
	}
	
	private void addListeners() {

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rbRouge.isSelected()) {
            		owner.setClass_Id("03");
                    JOptionPane.showMessageDialog(
                    		SelectPopup.this,
                            "도적을 선택했습니다."
                    );
                    owner.classlook("03");
            	}
            	else if(rbWarrior.isSelected()) {
            		owner.setClass_Id("01");
                    JOptionPane.showMessageDialog(
                    		SelectPopup.this,
                            "전사를 선택했습니다."
                    );
                    owner.classlook("01");
            	}
            	else if(rbwizard.isSelected()) {
            		owner.setClass_Id("02");
                    JOptionPane.showMessageDialog(
                    		SelectPopup.this,
                            "마법사를 선택했습니다."
                    );
                    owner.classlook("02");
            	}

            	dispose();
            }
        });
        btnCancle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    

}
