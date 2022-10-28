
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ClosestPoint {
    public static void main(String[] args) {
        Coordinate[] P = new Coordinate[] {
                new Coordinate(110, 13),
                new Coordinate(112, 30),
                new Coordinate(41, 52),
                new Coordinate(15, 13),
                new Coordinate(2, 1),
                new Coordinate(19, 6),
                new Coordinate(105, 131),
                new Coordinate(20, 12),
                new Coordinate(194, 63)

        };
        int k = 4;
        Coordinate.Kclosest(P, P.length, k);

    }

}

class PointAndDistance {
    public float distance;
    public Coordinate first;
    public Coordinate second;

    PointAndDistance(float dis, Coordinate p1, Coordinate p2) {
        distance = dis;
        first = new Coordinate(p1.x, p1.y);
        second = new Coordinate(p2.x, p2.y);
    }

    public PointAndDistance() {
    }

}

class Coordinate {
    public int x;
    public int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static PriorityQueue<PointAndDistance> points = new PriorityQueue<PointAndDistance>(new CompareResult());

    public static float dist(Coordinate p1, Coordinate p2) {
        float distance = (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
                (p1.y - p2.y) * (p1.y - p2.y));

        PointAndDistance ret = new PointAndDistance(distance, p1, p2);
        points.add(ret);

        return distance;
    }

    public static PointAndDistance MinimumDistance(Coordinate[] P, int n) {
        float minimum = Float.MAX_VALUE;
        float currMin = 0;
        Coordinate p1 = P[0];
        Coordinate p2 = P[0];
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                currMin = dist(P[i], P[j]);
                if (currMin < minimum) {
                    minimum = currMin;
                    p1 = P[i];
                    p2 = P[j];
                }
            }
        }
        PointAndDistance ret = new PointAndDistance(minimum, p1, p2);
        return ret;
    }

    public static PointAndDistance crossClosest(Coordinate[] cross, int size, float distance) {
        float minimum = distance;

        Arrays.sort(cross, 0, size, new CompareY());

        Coordinate p1 = cross[0];
        Coordinate p2 = cross[0];

        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && (cross[j].y - cross[i].y) < minimum; ++j) {
                if (dist(cross[i], cross[j]) < minimum) {
                    minimum = dist(cross[i], cross[j]);
                    p1 = cross[i];
                    p2 = cross[j];
                }
            }
        }
        PointAndDistance ret = new PointAndDistance(minimum, p1, p2);

        return ret;
    }

    public static PointAndDistance ClosestPoints(Coordinate[] xSortedPoints, Coordinate[] ySortedPoints,
            int n) {

        if (n <= 3) {
            return MinimumDistance(xSortedPoints, n);
        }
        int mid = n / 2;
        Coordinate midPoint = xSortedPoints[mid];
        Coordinate[] ySortedLeft = new Coordinate[mid + 2];
        Coordinate[] ySortedRight = new Coordinate[n - mid];
        int left = 0, right = 0;

        for (int i = 0; i < n; i++) {
            if (ySortedPoints[i].x <= midPoint.x)
                ySortedLeft[left++] = ySortedPoints[i];
            else
                ySortedRight[right++] = ySortedPoints[i];
        }

        PointAndDistance left_distance = ClosestPoints(xSortedPoints, ySortedPoints, mid);
        PointAndDistance right_distance = ClosestPoints(xSortedPoints, ySortedPoints, n - mid);
        PointAndDistance distance;
        if (left_distance.distance < right_distance.distance) {
            distance = left_distance;
        } else {
            distance = right_distance;
        }

        Coordinate[] cross = new Coordinate[n];

        int j = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(ySortedPoints[i].x - midPoint.x) < distance.distance) {
                cross[j] = ySortedPoints[i];
                j++;
            }
        }
        PointAndDistance s = new PointAndDistance();
        s = crossClosest(cross, j, distance.distance);
        if (s.distance < distance.distance) {
            return s;
        } else {
            return distance;
        }
    }

    public static PointAndDistance closest(Coordinate[] P, int n) {
        Coordinate[] xSorted = new Coordinate[n];
        Coordinate[] ySorted = new Coordinate[n];
        for (int i = 0; i < n; i++) {
            xSorted[i] = P[i];
            ySorted[i] = P[i];
        }
        Arrays.sort(xSorted, 0, n, new CompareX());
        Arrays.sort(ySorted, 0, n, new CompareY());

        return ClosestPoints(xSorted, ySorted, n);
    }

    public static void Kclosest(Coordinate[] P, int n, int k) {
        Coordinate.closest(P, P.length);
        // PointAndDistance previous = points.peek();
        System.out.println(points.size());
        for (int i = 1; i < points.size() + 1; i++) {
            PointAndDistance point = points.poll();
        //     if (i != 1 && previous == point) {
        //         previous = points.poll();
        //         continue;
        //     }
        //     previous = points.poll();

            System.out.println("distance :" +
                    point.distance + " between " + point.first.x + "," + point.first.y + " and " + point.second.x + ","
                    + point.second.y);
        }
    }

}

class CompareX implements Comparator<Coordinate> {

    @Override
    public int compare(Coordinate pointA, Coordinate pointB) {
        return Integer.compare(pointA.x, pointB.x);
    }

}

class CompareY implements Comparator<Coordinate> {
    @Override
    public int compare(Coordinate pointA, Coordinate pointB) {
        return Integer.compare(pointA.y, pointB.y);
    }

}

class CompareResult implements Comparator<PointAndDistance> {
    @Override
    public int compare(PointAndDistance A, PointAndDistance B) {
        return Float.compare(A.distance, B.distance);
    }

}