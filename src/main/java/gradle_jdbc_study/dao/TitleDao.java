package gradle_jdbc_study.dao;

import java.util.List;

import gradle_jdbc_study.dto.Title;

public interface TitleDao {
		List<Title> selectTitleByAll();
		Title selectTitleByNo(Title dept);
		int insertTitle(Title dept);
		int deleteTitle(Title dept);
		int updateTitle(Title dept);

}
