package inkball.object;

import java.util.ArrayList;
import java.util.List;

import inkball.App;
import inkball.Ball;
import processing.core.PVector;

public class LineObject extends StaticObject {
    int lineWidth = 10;
    private List<PVector[]> lines = new ArrayList<>();
    private List<int[]> points = new ArrayList<>();

    public LineObject() {
    }

    public void draw(App app) {
        for (PVector[] line : lines) {
            app.line(line[0].x, line[0].y, line[1].x, line[1].y);
        }
    }

    public void addPoints(int x, int y) {
        points.add(new int[] { x, y });
        if (points.size() > 4) {
            addLine(points);
            points = new ArrayList<>();
            points.add(new int[] { x, y }); // keep the last x, y. Make a continuous curve.
        }
    }

    public void addLine(List<int[]> points) {

        int maxX, maxY, minX, minY;
        minX = points.get(0)[0];
        minY = points.get(0)[1];
        maxX = points.get(points.size() - 1)[0];
        maxY = points.get(points.size() - 1)[1];
        PVector s1 = new PVector(minX, minY);
        PVector s2 = new PVector(maxX, maxY);
        PVector[] tmp = new PVector[] { s1, s2 };
        lines.add(tmp);
    }

    public void interactWithBall(App app, Ball ball) {
        for (PVector[] line : lines) {
            if (lineIntersect(ball, line)) {
                float midX = (line[0].x + line[1].x) / 2;
                float midY = (line[0].y + line[1].y) / 2;
                float[] center = new float[] { midX, midY };
                PVector midPointToBall = new PVector(ball.getCenter()[0] - center[0],
                        ball.getCenter()[1] - center[1]);

                PVector lineDirection = PVector.sub(line[1], line[0]);
                PVector normVec = new PVector(-lineDirection.y, lineDirection.x).normalize();

                float magN1 = PVector.add(midPointToBall, normVec).mag();
                float magN2 = PVector.sub(midPointToBall, normVec).mag();

                if (magN1 > magN2) {
                    ball.reflect(PVector.mult(normVec, -1));
                } else {
                    ball.reflect(normVec);
                }
                break;
            }
        }
    }

    public void interactWithBall(App app, List<Ball> ball, String BallColor) {
    }

    public boolean intersect(Ball ball) {
        for (PVector[] line : lines) {
            if (lineIntersect(ball, line)) {
                return true;
            }
        }
        return false;
    }

    public boolean lineIntersect(Ball ball, PVector[] lineVecs) {
        PVector ballPos = ball.nextFramePositionVec();
        float dist1 = PVector.sub(ballPos, lineVecs[0]).mag();
        float dist2 = PVector.sub(ballPos, lineVecs[1]).mag();
        float distLine = PVector.sub(lineVecs[0], lineVecs[1]).mag();
        float radius = ball.getRadius();
        if (dist1 + dist2 < distLine + radius) {
            return true;
        }
        return false;
    }

}