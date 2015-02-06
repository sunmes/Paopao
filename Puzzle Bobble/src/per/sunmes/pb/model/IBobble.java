package per.sunmes.pb.model;

public interface IBobble {

	String gameName = "PuzzleBobble";

	int windowWidth = 310;
	int windowHeight = 500;

	int windowOffx = 5;
	int windowOffy = 31;

	int zoneWidth = windowWidth - windowOffx * 2;
	int zoneHeight = windowHeight - IBobble.windowOffx - IBobble.windowOffy;

	int xBallCount = 10;
	int yBallCount = 13;

	int radius = zoneWidth / xBallCount;
	int yRadius = radius - 3;

	int speed = 5;
}
