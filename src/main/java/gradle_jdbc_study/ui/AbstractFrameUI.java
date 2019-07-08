package gradle_jdbc_study.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import gradle_jdbc_study.ui.content.AbstractPanelContent;
import gradle_jdbc_study.ui.list.AbstractListPanel;

@SuppressWarnings("serial")
public abstract class AbstractFrameUI<T> extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel pMain;
	protected AbstractPanelContent<T> pContent;
	protected AbstractListPanel<T> pList;
	protected List<T> itemLists;
	
	protected JButton btnAdd;
	private JButton btnCancel;

	public AbstractFrameUI() {
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pMain = new JPanel();
		pMain.setLayout(new BorderLayout());
		contentPane.add(pMain, BorderLayout.CENTER);
		
		JPanel pBtns = new JPanel();
		pMain.add(pBtns, BorderLayout.SOUTH);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);
	}

	public void setpContent() {
		getContentPanel();
		getListPanel();
		
		pMain.add(this.pContent, BorderLayout.CENTER);
		contentPane.add(this.pList, BorderLayout.SOUTH);
		addPopupMenu();
	}

	protected void refreshList() {
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel();
		}
		if (e.getSource() == btnAdd) {
			if (e.getActionCommand().equals("추가")) {
				actionPerformedBtnAdd();
			}
			if (e.getActionCommand().equals("수정")) {
				actionPerformedBtnUpdate();
			}
		}
	}
	
	private void addPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem mntmPopUpdate = new JMenuItem("수정");
		mntmPopUpdate.addActionListener(popupListener);
		popupMenu.add(mntmPopUpdate);

		JMenuItem mntmPopDelete = new JMenuItem("삭제");
		mntmPopDelete.addActionListener(popupListener);
		popupMenu.add(mntmPopDelete);

		pList.setPopupMenu(popupMenu);
	}

	ActionListener popupListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("수정")) {
				actionContentUpdateUI();
			}
			if (e.getActionCommand().equals("삭제")) {
				actionPerformedBtnDelete();
			}
		}
	};
	
	protected void actionPerformedBtnCancel() {
		pContent.clearComponent(getNextNo());	
	}
	
	protected abstract void getContentPanel();
	protected abstract void getListPanel();

	protected abstract int getNextNo();
	protected abstract void reloadItemList();
	protected abstract void actionContentUpdateUI();
	protected abstract void actionPerformedBtnUpdate();
	protected abstract void actionPerformedBtnAdd();

	protected abstract void actionPerformedBtnDelete();
}
