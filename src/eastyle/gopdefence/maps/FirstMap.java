package eastyle.gopdefence.maps;

public class FirstMap {
	private String imageMap = "map1.jpg";
	private float gameZoneX = 482.0f;
	private float gameZoneY = 482.0f;
	private float gameZoneMarginBottom = 100.0f;
	private float newScaleXmapElementSize = 1.0f;
	private float mapElementSize = 40.0f;
	private static int waveLevel = 1;
	/* Towers img, attck, range, delay, coast */
	private static Object[][] testTowerProperties = {
		new Object[] { "blue_tower.png", new Integer(25), new Float(100), new Integer(300), new Integer(100) },
		new Object[] { "blue_tower.png", new Integer(15), new Float(100), new Integer(300), new Integer(65) },
		new Object[] { "blue_tower.png", new Integer(10), new Float(100), new Integer(300), new Integer(35) },
		new Object[] { "blue_tower.png", new Integer(5), new Float(100), new Integer(300), new Integer(20) } };

	/* Targets img, speed, heals, coast, count, delay */
	private static Object[][] testTargetProperties = {
		new Object[] { "red_tower.png", new Integer(110), new Integer(300), new Integer(5), new Integer(25), new Integer(200)  },
		new Object[] { "red_tower.png", new Integer(110), new Integer(500), new Integer(10), new Integer(25), new Integer(200)  },
		new Object[] { "red_tower.png", new Integer(110), new Integer(700), new Integer(15), new Integer(25), new Integer(1000)  },
		new Object[] { "red_tower.png", new Integer(110), new Integer(1500), new Integer(20), new Integer(5), new Integer(1000)  } };
	public FirstMap() {

	}

	public String getImageMap() {
		return imageMap;
	}

	public void setImageMap(String imageMap) {
		this.imageMap = imageMap;
	}

	public float getGameZoneX() {
		return gameZoneX;
	}

	public void setGameZoneX(float gameZoneX) {
		this.gameZoneX = gameZoneX;
	}

	public float getGameZoneY() {
		return gameZoneY;
	}

	public void setGameZoneY(float gameZoneY) {
		this.gameZoneY = gameZoneY;
	}

	public float getGameZoneMarginBottom() {
		return gameZoneMarginBottom;
	}

	public void setGameZoneMarginBottom(float gameZoneMarginBottom) {
		this.gameZoneMarginBottom = gameZoneMarginBottom;
	}

	public float getNewScaleXmapElementSize() {
		return newScaleXmapElementSize;
	}

	public void setNewScaleXmapElementSize(float newScaleXmapElementSize) {
		this.newScaleXmapElementSize = newScaleXmapElementSize;
	}

	public float getMapElementSize() {
		return mapElementSize;
	}

	public void setMapElementSize(float mapElementSize) {
		this.mapElementSize = mapElementSize;
	}

	public static Object[][] getTestTowerProperties() {
		return testTowerProperties;
	}

	public static int getWaveLevel() {
		return waveLevel;
	}

	public static void setWaveLevel(int waveLevel) {
		FirstMap.waveLevel = waveLevel;
	}

	public static Object[][] getTestTargetProperties() {
		return testTargetProperties;
	}
}
