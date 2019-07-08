package gradle_jdbc_study.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.daoimpl.TitleDaoImpl;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.ui.content.PanelTitle;
import gradle_jdbc_study.ui.list.TitleList;

@SuppressWarnings("serial")
public class TitleFrameUI extends AbstractFrameUI<Title> {
	private TitleDao dao = new TitleDaoImpl();
	
	public TitleFrameUI() {
		reloadItemList();
		setTitle("직책관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		setpContent();
	}

	
	@Override
	protected void getContentPanel() {
		pContent = new PanelTitle();
		
		int nextDeptNo = getNextNo();
		pContent.getTfNo().setText(String.format("T%03d", nextDeptNo));
		pContent.getTfNo().setEditable(false);		
	}

	@Override
	protected void getListPanel() {
		pList = new TitleList("직책 목록");
		reloadItemList();
		pList.setItemList(itemLists);
		pList.reloadData();
	}

	@Override
	protected int getNextNo() {
		int nextDeptNo;
		if (itemLists.size()==0) {
			nextDeptNo = 0;
		}else {
			nextDeptNo = itemLists.get(itemLists.size()-1).getTitleNo();
		}
		return nextDeptNo+1;
	}

	@Override
	protected void reloadItemList() {
		itemLists = dao.selectTitleByAll();		
	}

	@Override
	protected void actionContentUpdateUI() {
		Title title = pList.getSelectedItem();
		pContent.setItem(title);
		pContent.getTfNo().setEditable(false);
		btnAdd.setText("수정");		
	}

	@Override
	protected void actionPerformedBtnUpdate() {
		Title title = pContent.getItem();
		int res = dao.updateTitle(title);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, title + "수정 실패");
			return;
		}
		JOptionPane.showMessageDialog(null, title + "수정 되었습니다.");
		refreshList();
		btnAdd.setText("추가");
		pContent.clearComponent(getNextNo());		
	}

	@Override
	protected void actionPerformedBtnAdd() {
		Title title = pContent.getItem();
		int res = dao.insertTitle(title);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, title + "추가 실패");
			return;
		}
		JOptionPane.showMessageDialog(null, title.getTitleName() + "추가 되었습니다.");
		refreshList();
		pContent.clearComponent(getNextNo());		
	}

	@Override
	protected void actionPerformedBtnDelete() {
		Title title = pList.getSelectedItem();
		int res = dao.deleteTitle(title);
		if (res == -1) {
			JOptionPane.showMessageDialog(null, title + "삭제 실패");
			return;
		}
		JOptionPane.showMessageDialog(null, title + "삭제 되었습니다.");
		refreshList();		
	}

}
