package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;
import lombok.Getter;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

@AInputData(day = 2, year = 2022)
public class Day2 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        AtomicInteger points = new AtomicInteger(0);

        for(String line : inputHelper.getInput().lines().toList()){
            String[] split = line.split(" ");

            Response response = Response.valueOf(split[1]);
            Request request = Request.valueOf(split[0]);

            if(isDraw(request, response)){
                points.getAndAdd(response.getPoints() + 3);
            } else if (isWin(request, response)){
                points.getAndAdd(response.getPoints() + 6);
            } else {
                points.getAndAdd(response.getPoints());
            }
        }

        return String.valueOf(points.get());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        AtomicInteger points = new AtomicInteger(0);

        for(String line : inputHelper.getInput().lines().toList()) {
            String[] split = line.split(" ");

            Response response = Response.valueOf(split[1]);
            Request request = Request.valueOf(split[0]);

            switch (response){
                case X -> {
                    points.getAndAdd(request.getLoose().getPoints());
                }
                case Y -> {
                    points.getAndAdd(request.getDraw().getPoints() + 3);
                }
                case Z -> {
                    points.getAndAdd(request.getWin().getPoints() + 6);
                }
            }
        }

        return String.valueOf(points.get());
    }

    private boolean isWin(Request request, Response response){
        if(request == Request.C && response == Response.X) return true;
        if(request == Request.B && response == Response.Z) return true;

        return request == Request.A && response == Response.Y;
    }

    private boolean isDraw(Request request, Response response){
        return request.ordinal() == response.ordinal();
    }

    public enum Response {
        X(1),
        Y(2),
        Z(3);

        private int points;

        Response(int points){
            this.points = points;
        }

        public int getPoints() {
            return points;
        }
    }

    public enum Request {
        A(Response.Y, Response.Z, Response.X),
        B(Response.Z, Response.X, Response.Y),
        C(Response.X, Response.Y, Response.Z);

        @Getter private Response win;
        @Getter private Response loose;
        @Getter private Response draw;

        Request(Response win, Response loose, Response draw){
            this.win = win;
            this.loose = loose;
            this.draw = draw;
        }

    }
}
