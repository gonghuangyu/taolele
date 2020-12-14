package cn.tedu.mapper;

public class Point {
    private int point;
    private String userId;
    private String game;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Point{" +
                "point=" + point +
                ", userId='" + userId + '\'' +
                ", game='" + game + '\'' +
                '}';
    }
}
