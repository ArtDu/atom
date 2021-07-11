package ru.atom.geometry;

public class Bar implements Collider {
    Point firstCorner;
    Point secondCorner;

    public Point getFirstCorner() {
        return firstCorner;
    }

    public Point getSecondCorner() {
        return secondCorner;
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if (firstCornerX > secondCornerX) {
            int temp = secondCornerX;
            secondCornerX = firstCornerX;
            firstCornerX = temp;
        }

        if (firstCornerY > secondCornerY) {
            int temp = secondCornerY;
            secondCornerY = firstCornerY;
            firstCornerY = temp;
        }

        this.firstCorner = new Point(firstCornerX, firstCornerY);
        this.secondCorner = new Point(secondCornerX, secondCornerY);
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        return this.firstCorner.equals(bar.firstCorner)
                && this.secondCorner.equals(bar.secondCorner);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point otherPoint = ((Point) other);
            return this.getFirstCorner().getX() <= otherPoint.getX()
                    && otherPoint.getX() <= this.getSecondCorner().getX()
                    && this.getFirstCorner().getY() <= otherPoint.getY()
                    && otherPoint.getY() <= this.getSecondCorner().getY();
        } else if (other instanceof Bar) {
            // To check if either rectangle is actually a line
            // For example :  l1 ={-1,0}  r1={1,1}  l2={0,-1}  r2={0,1}
            Bar otherBar = ((Bar) other);

            // If one rectangle is on left side of other
            if (this.getFirstCorner().getX() > otherBar.getSecondCorner().getX()
                    || otherBar.getFirstCorner().getX() > this.getSecondCorner().getX()) {
                return false;
            }

            // If one rectangle is above other
            if (this.getFirstCorner().getY() > otherBar.getSecondCorner().getY()
                    || otherBar.getFirstCorner().getY() > this.getSecondCorner().getY()) {
                return false;
            }
            return true;
        }
        return false;
    }

}
