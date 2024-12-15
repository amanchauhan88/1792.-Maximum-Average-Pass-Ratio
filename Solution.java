import java.util.PriorityQueue;

class Solution {
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        // Priority queue (max heap) to store the classes based on their potential improvement
        PriorityQueue<double[]> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));

        // Initialize the heap with the improvement in pass ratio for each class
        for (int[] cls : classes) {
            int pass = cls[0];
            int total = cls[1];
            double improvement = improvementRatio(pass, total);
            maxHeap.offer(new double[] { improvement, pass, total });
        }

        // Allocate the extra students to maximize the average pass ratio
        while (extraStudents > 0) {
            double[] top = maxHeap.poll();
            double improvement = top[0];
            int pass = (int) top[1];
            int total = (int) top[2];

            // Add one extra student to this class
            pass++;
            total++;
            extraStudents--;

            // Recalculate the improvement and push back into the heap
            maxHeap.offer(new double[] { improvementRatio(pass, total), pass, total });
        }

        // Calculate the final average pass ratio
        double totalAverage = 0.0;
        while (!maxHeap.isEmpty()) {
            double[] top = maxHeap.poll();
            int pass = (int) top[1];
            int total = (int) top[2];
            totalAverage += (double) pass / total;
        }

        return totalAverage / classes.length;
    }

    // Helper method to calculate the improvement in pass ratio when one student is added
    private double improvementRatio(int pass, int total) {
        double currentRatio = (double) pass / total;
        double newRatio = (double) (pass + 1) / (total + 1);
        return newRatio - currentRatio;
    }
}
