package eastyle.gopdefence.maps;

public class FirstMap {
	public static String imageMap = "m01.jpg";
	private float gameZoneX = 482.0f;
	private float gameZoneY = 482.0f;
	private float gameZoneMarginBottom = 100.0f;
	private float newScaleXmapElementSize = 1.0f;
	public static float mapElementSize = 36.0f;
	private static int waveLevel = 1;
	/* Towers img, attck, range, delay, coast, attackType */
	private static Object[][] testTowerProperties = {
			new Object[] { "blue_tower.png", new Integer(20), new Float(100),
					new Integer(300), new Integer(100), new Integer(0) },
			new Object[] { "blue_tower.png", new Integer(15), new Float(100),
					new Integer(300), new Integer(150), new Integer(1) },
			new Object[] { "blue_tower.png", new Integer(10), new Float(100),
					new Integer(600), new Integer(50), new Integer(2) },
			new Object[] { "blue_tower.png", new Integer(5), new Float(100),
					new Integer(300), new Integer(100), new Integer(3) } };

	/* Targets img, speed, heals, coast, count, delay */
	private static Object[][] testTargetProperties = {
			new Object[] { "red_tower.png", new Integer(70), new Integer(200),
					new Integer(5), new Integer(10), new Integer(2000) },
					
			new Object[] { "red_tower.png", new Integer(80), new Integer(500),
					new Integer(10), new Integer(25), new Integer(3000) },
			new Object[] { "red_tower.png", new Integer(80), new Integer(700),
					new Integer(15), new Integer(25), new Integer(3000) },
			new Object[] { "red_tower.png", new Integer(80),
					new Integer(1500), new Integer(20), new Integer(5),
					new Integer(2500) } };
	/* */
	public static final int[][] marshrut = { { 0, 2 }, { 2, 2 }, { 2, 5 },
			{ 6, 5 }, { 6, 13 }, { 21, 13 }, { 21, 11 }, { 18, 11 }, { 18, 9 },
			{ 21, 9 }, { 21, 6 }, { 15, 6 }, { 15, 9 }, { 9, 9 }, { 9, 5 },
			{ 15, 5 }, { 15, 4 }, { 20, 4 }, { 20, 2 }, { 22, 2 } };

	public FirstMap() {

	}

	public String getImageMap() {
		return imageMap;
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
