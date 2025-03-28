package pageObject;

import supporter.XLSReader;

public class PageMethods {
	public static XLSReader cellval = new XLSReader("src/main/resources/data/login.xlsx");
	static int ROWNUM = 2;

	public static void happyPathLogin(String sheetName) throws Exception {
		System.out.println();
		String username = cellval.getCellData(sheetName, "Username", ROWNUM);
		System.out.println("username: " + username);
		PageUtils.enterUsername(username);
		String password = cellval.getCellData(sheetName, "Password", ROWNUM);
		System.out.println("password: " + password);
		PageUtils.enterPassword(password);
		PageUtils.clicklogin();
		PageUtils.verifyLoginSuccess();
	}

	public static void errorLogin(String sheetName) throws Exception {
		for (int RowNum = 2; RowNum <= cellval.getRowCount(sheetName); RowNum++) {
			String username = cellval.getCellData(sheetName, "Username", RowNum);
			System.out.println("username: " + username);
			PageUtils.enterUsername(username);
			String password = cellval.getCellData(sheetName, "Password", RowNum);
			System.out.println("password: " + password);
			PageUtils.enterPassword(password);

			PageUtils.clicklogin();
			PageUtils.verifyLoginFailure();
		}
	}

	public static void verifybrokenProductImage(String sheetName) throws Exception {
		String username = cellval.getCellData(sheetName, "Username", ROWNUM);
		PageUtils.enterUsername(username);
		String password = cellval.getCellData(sheetName, "Password", ROWNUM);
		PageUtils.enterPassword(password);
		PageUtils.clicklogin();
		PageUtils.verifyLoginSuccess();
		PageUtils.verifyBrokenImages();
	}

	public static void verifybrokenLinks(String sheetName) throws Exception {
		String username = cellval.getCellData(sheetName, "Username", ROWNUM);
		PageUtils.enterUsername(username);
		String password = cellval.getCellData(sheetName, "Password", ROWNUM);
		PageUtils.enterPassword(password);
		PageUtils.clicklogin();
		PageUtils.verifyLoginSuccess();
		PageUtils.verifyBrokenLinks(sheetName);

	}

}
