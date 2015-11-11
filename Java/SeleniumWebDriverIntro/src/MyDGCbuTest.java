import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MyDGCbuTest {

	// Parameters for test
	String targetUsername = null;
	String targetPassword = null;
	String targetCatID = null;
	String targetLocationByName = null;

	// Test framework member variables
	WebDriver driver = null;
	String username = null;
	String password = null;
	List<WebElement> feature_list = null;

	public static void main(String[] args) {

		MyDGCbuTest mdgt = new MyDGCbuTest();

		// Do you want to place an order?
		mdgt.placeTheOrderWithCredentialsAndCatId();

		// Which browser are using
		// mdgt.useFirefox();
		mdgt.useChrome();

		// Search for an archive...
		mdgt.login();
		mdgt.goToAdvancedSearch();
		mdgt.drawRectangleAOIOverLongBeach();
		mdgt.scanSifResultsGrid();
		mdgt.scanAndSelectArchiveFeatureFromList();

		// mdgt.enterRecipeParameters();
		// mdgt.enterAdvancedParameters();

		// Add a specific type of order
		// mdgt.order_L1_Pan_WV02_1B();
		// mdgt.order_L3_Pan_WV02_3D();
		// mdgt.order_L3_Pan_WV02_3F();
		// mdgt.order_L3_Pan_WV02_3G();
		// mdgt.order_1001_Pan_WV02_25m();
		// mdgt.order_1001_Pan_WV02_10m();
		// mdgt.order_1001_Pan_WV02_4m();
		// mdgt.order_1004_WV02_25m();
		// mdgt.order_1005_WV02_10m();
		mdgt.order_1006_WV02_4m();

		// Submit your order
		mdgt.addToCartAndClose();
		mdgt.goToCart();
		mdgt.submitOrdersAndClose();
		mdgt.logout();
		mdgt.quit();

	}

	public void placeTheOrderWithCredentialsAndCatId() {

		this.targetUsername = "MRH_User";
		this.targetPassword = "test";

		// A WV02 archive feature over Long Beach for test
		this.targetLocationByName = "Long Beach, CA";
		this.targetCatID = "103001004A43D900";
	}

	public void useFirefox() {

		// Get past the DG proxy for test
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setSslProxy("moz-proxy://gdenwcflgmt.digitalglobe.com" + ":" + 8080);
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(CapabilityType.PROXY, proxy);

		// Open the firefox driver and set timeout for inaction
		this.driver = new FirefoxDriver(dc);

	}

	public void useChrome() {

		// Get past the DG proxy for test
		// org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		// proxy.setSslProxy("moz-proxy://gdenwcflgmt.digitalglobe.com" + ":" +
		// 8080);
		// DesiredCapabilities dc = DesiredCapabilities.firefox();
		// dc.setCapability(CapabilityType.PROXY, proxy);

		// Open the firefox driver and set timeout for inaction
		// this.driver = new ChromeDriver(dc);
		this.driver = new ChromeDriver();

	}

	public void login() {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// Get to MyDG in #INT
		driver.get("https://services-int.digitalglobe.com/myDigitalGlobe/login");
		wait(3);
		driver.manage().window().maximize();

		// Get past the Ad Gov Portal
		WebElement adGovPortalCloseButton = driver
				.findElement(By.xpath(".//*[@id='loginAlert']/div/div/div[3]/button"));
		adGovPortalCloseButton.click();

		// Enter credentials and log in
		WebElement cbuUsernameText = driver.findElement(By.xpath("//*[@id='username']"));
		cbuUsernameText.sendKeys(this.targetUsername);
		wait(1);
		WebElement cbuPasswordText = driver.findElement(By.xpath("//*[@id='pw']"));
		cbuPasswordText.sendKeys(this.targetPassword);
		wait(2);

		// Click the Log In button
		WebElement loginButton = driver.findElement(By.xpath(".//*[@id='loginButton']"));
		loginButton.click();
		wait(1);

	}

	public void logout() {

		WebElement userMenuDropdown = driver.findElement(By.xpath("//*[@id='test_userMenu']/span[1]"));
		userMenuDropdown.click();
		wait(2);

		WebElement logout = driver.findElement(By.xpath("//*[@id='logout']"));
		logout.click();
		wait(2);

	}

	public void quit() {
		this.driver.quit();
	}

	public void wait(int seconds) {

		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void goToAdvancedSearch() {

		WebElement searchTextWindow = driver.findElement(By.xpath("//*[@id='leaflet-control-geosearch-qry']"));
		searchTextWindow.sendKeys(this.targetLocationByName);

		WebElement searchButton = driver.findElement(By.xpath("//*[@id='leaflet-control-geosearch-submit-qry']"));
		searchButton.click();

		WebElement myImageryButton = driver.findElement(By.xpath("//*[@id='test_myImagery']/span"));
		myImageryButton.click();

		WebElement advancedSearchButton = driver.findElement(By.xpath("//*[@id='test_sifSearch']/span"));
		advancedSearchButton.click();

	}

	public void drawRectangleAOIOverLongBeach() {
		driver.findElement(By.xpath("//*[@id='mapDiv']/div[3]/div[1]/div[5]/a/i")).click();
		driver.findElement(By.xpath("//*[@id='sif-search-rect']")).click();
		wait(2);
		WebElement drawAOIButton = driver.findElement(By.xpath("//*[@id='mapDiv']/div[3]/div[1]/div[5]/a/i"));
		Actions actions = new Actions(driver);
		actions.moveToElement(drawAOIButton).build().perform();
		actions.moveByOffset(30, 0).build().perform();
		actions.clickAndHold().build().perform();
		actions.moveByOffset(600, 400).build().perform();
		actions.release().build().perform();
		wait(7);
	}

	public void scanSifResultsGrid() {

		driver.switchTo().defaultContent();
		WebElement archiveRows = driver.findElement(By.xpath("//*[@id='sifResultsGrid']/table/tbody"));

		// Get List of rows
		List<WebElement> rows = archiveRows.findElements(By.tagName("tr"));
		java.util.Iterator<WebElement> i = rows.iterator();
		this.feature_list = rows;
		wait(1);
	}

	public void scanAndSelectArchiveFeatureFromList() {

		// Scan feature list for a particular archive CatID
		java.util.Iterator<WebElement> element = this.feature_list.iterator();

		// Count the rows to find the CatID arg and click the
		// advFeaturOptionsButton
		int i = 0;
		while (element.hasNext()) {
			WebElement row = element.next();
			i++;

			if (row.getAttribute("data-featureid").equals(this.targetCatID)) {
				driver.switchTo().defaultContent();

				// Print which row of the Feature table you are selecting
				// System.out.println("i = " + i);

				// This click action displays the dropdown just fine
				WebElement button = driver.findElement(By.xpath(
						"//*[@id='sifResultsGrid']/table/tbody/tr[" + i + "]/td/*[@id='advFeatureOptionsButton']/a/i"));
				button.click();

				WebElement addImageToCart = driver.findElement(By
						.xpath("//*[@id='sifResultsGrid']/table/tbody/tr[" + i + "]//*[@id='menuOptionOrderImage']/a"));
				addImageToCart.click();
			}
		}

		wait(10);

	}

	public void addToCartAndClose() {

		// Due to multiple ids of "submitBtn" need to you absolute path with id
		// to help
		WebElement addToCart = driver.findElement(By.xpath("html/body/div[9]/div/div/form/div[3]/button[1]"));
		addToCart.click();
		wait(1);
		WebElement closeButton = driver.findElement(By.xpath(".//*[@id='mapPage']/div[27]/div/div/div[2]/button[1]"));
		closeButton.click();
		wait(1);
	}

	public void goToCart() {

		WebElement cartIcon = driver
				.findElement(By.xpath("//*[@id='mapPage']/div[1]/header/div/div/nav/div/div[2]/ul[2]/li[1]/a/i"));
		cartIcon.click();

		WebElement cartSelection = driver.findElement(By.xpath("//*[@id='test_cart']"));
		cartSelection.click();
		wait(2);

	}

	public void submitOrdersAndClose() {

		WebElement submitOrdersButton = driver.findElement(By.xpath("//*[@id='cartOrderBtn']"));
		submitOrdersButton.click();
		wait(2);

		WebElement closeButton = driver.findElement(By.xpath("//*[@id='cartCancelBtn']"));
		closeButton.click();
		wait(2);

	}

	public void enterRecipeParameters() {

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhCbuLongBeach");

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhCbuLongBeach Comments");

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[2]"));
		endUse.click();

		WebElement deliverTo = driver.findElement(By.xpath(".//*[@id='param_deliverTo']/option[2]"));
		deliverTo.click();

		WebElement productType = driver.findElement(By.xpath(".//*[@id='param_recipe']/option[2]"));
		productType.click();

	}

	public void enterAdvancedParameters() {

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhCbuLongBeachAdvancedOptions");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhCbuLongBeachAdvancedOptions Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();

		wait(1);

		// WebElement endUse =
		// driver.findElement(By.xpath(".//*[@id='endUse']/option[1]"));
		// endUse.click();
		// wait(1);

		// WebElement deliverTo =
		// driver.findElement(By.xpath(".//*[@id='param_deliverToAdvanced']/option[1]"));
		// deliverTo.click();
		// wait(1);

		// WebElement compression =
		// driver.findElement(By.xpath(".//*[@id='param_deliveryCompressionAdvanced']/option[1]"));
		// compression.click();
		// wait(1);

		/*
		 * WebElement productType =
		 * driver.findElement(By.xpath(".//*[@id='param_productType']/option[1]"
		 * )); productType.click(); wait(1);
		 * 
		 * WebElement productOption = driver.findElement(By.xpath(
		 * ".//*[@id='param_productOption']/option[1]")); productOption.click();
		 * wait(1);
		 * 
		 * WebElement bands =
		 * driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[1]")
		 * ); bands.click(); wait(1);
		 * 
		 * WebElement bitsPerPixel = driver.findElement(By.xpath(
		 * ".//*[@id='param_bitsPerPixel']/option[1]")); bitsPerPixel.click();
		 * wait(1);
		 * 
		 * WebElement dra =
		 * driver.findElement(By.xpath(".//*[@id='param_dra']/option[1]"));
		 * dra.click(); wait(1);
		 * 
		 * WebElement resamplingKernel = driver.findElement(By.xpath(
		 * ".//*[@id='param_resamplingKernel']/option[1]"));
		 * resamplingKernel.click(); wait(1);
		 * 
		 * WebElement mapProjection = driver.findElement(By.xpath(
		 * ".//*[@id='param_mapProjection']/option[1]")); mapProjection.click();
		 * wait(1);
		 * 
		 * WebElement requestedGSD = driver.findElement(By.xpath(
		 * ".//*[@id='param_requestedGsd']/option[1]")); requestedGSD.click();
		 * wait(1);
		 * 
		 * WebElement productGSD =
		 * driver.findElement(By.xpath(".//*[@id='param_productGsd']/option[2]")
		 * ); productGSD.click(); wait(1);
		 * 
		 * WebElement tilingMethod = driver.findElement(By.xpath(
		 * ".//*[@id='param_tilingSystem']/option[2]")); tilingMethod.click();
		 * wait(1);
		 * 
		 * //WebElement tilingSize =
		 * driver.findElement(By.xpath(".//*[@id='param_tilingSize']/option[2]")
		 * ); //tilingSize.click(); //wait(1);
		 * 
		 * WebElement fileFormat =
		 * driver.findElement(By.xpath(".//*[@id='param_tilingSize']/option[1]")
		 * ); fileFormat.click(); wait(1);
		 */

	}

	public void order_L1_Pan_WV02_1B() {

		/*
		 * Material Number: L1 Product Options: Pan Spacecraft: WV02 Product
		 * Name: 1B
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_L1_Pan_WV02_1B");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_L1_Pan_WV02_1B Comments");
		wait(1);

		// End Use is arbitrarily Agriculture
		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();

		wait(1);

	}

	public void order_L3_Pan_WV02_3D() {

		/*
		 * Material Number: L3 Product Options: Pan Spacecraft: WV02 Product
		 * Name: 3D
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_L3_Pan_WV02_3D");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_L3_Pan_WV02_3D Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		WebElement productType = driver
				.findElement(By.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Map_Scale_Ortho']"));
		productType.click();
		wait(1);

		// <option value="15030">1:12,000 Orthorectified (3D)</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15030']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);

	}

	public void order_L3_Pan_WV02_3F() {

		/*
		 * Material Number: L3 Product Options: Pan Spacecraft: WV02 Product
		 * Name: 3F
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_L3_Pan_WV02_3F");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_L3_Pan_WV02_3F Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		WebElement productType = driver
				.findElement(By.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Map_Scale_Ortho']"));
		productType.click();
		wait(1);

		// <option value="15031">1:5,000 Orthorectified (3F)</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15031']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);

	}

	public void order_L3_Pan_WV02_3G() {

		/*
		 * Material Number: L3 Product Options: Pan Spacecraft: WV02 Product
		 * Name: 3G
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_L3_Pan_WV02_3G");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_L3_Pan_WV02_3G Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		WebElement productType = driver
				.findElement(By.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Map_Scale_Ortho']"));
		productType.click();
		wait(1);

		// <option value="15032">1:4,800 Orthorectified (3G)</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15032']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);
	}

	public void order_1001_Pan_WV02_25m() {

		/*
		 * Material Number: 1001 Product Options: Pan Spacecraft: WV02 Product
		 * Name: Display - 25m
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_1001_Pan_WV02_25m");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_1001_Pan_WV02_25m Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		// <option data-ordersheetvalue="Advanced_Ortho_Vision"
		// value="15024">Advanced Ortho Vision</option>
		WebElement productType = driver.findElement(
				By.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Advanced_Ortho_Vision']"));
		productType.click();
		wait(1);

		// <option value="15033">Display - 25m</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15033']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);

	}

	public void order_1001_Pan_WV02_10m() {

		/*
		 * Material Number: 1002 Product Options: Pan Spacecraft: WV02 Product
		 * Name: Mapping - 10m
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_1001_Pan_WV02_10m");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_1001_Pan_WV02_10m Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		// <option data-ordersheetvalue="Advanced_Ortho_Vision"
		// value="15024">Advanced Ortho Vision</option>
		WebElement productType = driver.findElement(
				By.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Advanced_Ortho_Vision']"));
		productType.click();
		wait(1);

		// <option value="15034">Mapping - 10m</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15034']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);
	}

	public void order_1001_Pan_WV02_4m() {

		/*
		 * Material Number: 1003 Product Options: Pan Spacecraft: WV02 Product
		 * Name: Precision - 4m
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_1001_Pan_WV02_4m");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_1001_Pan_WV02_4m Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		// <option data-ordersheetvalue="Advanced_Ortho_Vision"
		// value="15024">Advanced Ortho Vision</option>
		WebElement productType = driver.findElement(
				By.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Advanced_Ortho_Vision']"));
		productType.click();
		wait(1);

		// <option value="15035">Precision - 4m</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15035']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);

	}

	public void order_1004_WV02_25m() {

		/*
		 * Material Number: 1004 Product Options: Pan Spacecraft: WV02 Product
		 * Name: Display - 25m
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_1004_WV02_25m");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_1004_WV02_25m Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		// <option data-ordersheetvalue="Advanced_Ortho_Vision_Premium"
		// value="15025">Advanced Ortho Vision Premium</option>
		WebElement productType = driver.findElement(By
				.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Advanced_Ortho_Vision_Premium']"));
		productType.click();
		wait(1);

		// <option value="15036">Display - 25m</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15036']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);
	}

	public void order_1005_WV02_10m() {

		/*
		 * Material Number: 1005 Product Options: Pan Spacecraft: WV02 Product
		 * Name: Mapping - 10m
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_1005_WV02_10m");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_1005_WV02_10m Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		// <option data-ordersheetvalue="Advanced_Ortho_Vision_Premium"
		// value="15025">Advanced Ortho Vision Premium</option>
		WebElement productType = driver.findElement(By
				.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Advanced_Ortho_Vision_Premium']"));
		productType.click();
		wait(1);

		// <option value="15037">Mapping - 10m</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15037']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);
	}

	public void order_1006_WV02_4m() {

		/*
		 * Material Number: 1006 Product Options: Pan Spacecraft: WV02 Product
		 * Name: Precision - 4m
		 */

		WebElement showAdvancedOptions = driver.findElement(By.xpath(".//*[@id='orderSettings']/div[3]/button[2]"));
		showAdvancedOptions.click();
		wait(1);

		WebElement orderNameText = driver.findElement(By.xpath(".//*[@id='orderName']"));
		orderNameText.sendKeys("mrhOrder_1006_WV02_4m");
		wait(1);

		WebElement commentText = driver.findElement(By.xpath(".//*[@id='orderComment']"));
		commentText.sendKeys("mrhOrder_1006_WV02_4m Comments");
		wait(1);

		WebElement endUse = driver.findElement(By.xpath(".//*[@id='endUse']/option[@value='AGR']"));
		endUse.click();
		wait(1);

		// <option data-ordersheetvalue="Advanced_Ortho_Vision_Premium"
		// value="15025">Advanced Ortho Vision Premium</option>
		WebElement productType = driver.findElement(By
				.xpath(".//*[@id='param_productType']/option[@data-ordersheetvalue='Advanced_Ortho_Vision_Premium']"));
		productType.click();
		wait(1);

		// <option value="15038">Precision - 4m</option>
		WebElement productOption = driver
				.findElement(By.xpath(".//*[@id='param_productOption']/option[@value='15038']"));
		productOption.click();
		wait(1);

		// <option value="15039" selected="">Pan</option>
		WebElement bands = driver.findElement(By.xpath(".//*[@id='param_imageBands']/option[@value='15039']"));
		bands.click();
		wait(1);
	}
}
