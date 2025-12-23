import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create a Scanner for reading input
        Scanner sc = new Scanner(System.in);
        
        // Read the number of test cases
        int t = sc.nextInt();
        
        // Loop through each test case
        for (int i = 0; i < t; i++) {
            // Read n (array size) and k (the upper limit constant)
            int n = sc.nextInt();
            
            // NOTE: k must be 'long' because it can be up to 10^18. 
            // An 'int' would overflow and cause wrong answers.
            long k = sc.nextLong();
            
            // Initialize arrays q and r
            int[] q = new int[n];
            int[] r = new int[n];
            
            // Read array q
            for (int j = 0; j < n; j++) q[j] = sc.nextInt();
            // Read array r
            for (int j = 0; j < n; j++) r[j] = sc.nextInt();
            
            // --- STEP 1: SORTING ---
            // We sort both arrays because the optimal strategy (Greedy) 
            // requires picking the smallest available numbers to maximize operations.
            Arrays.sort(q);
            Arrays.sort(r);
            
            // --- STEP 2: BINARY SEARCH ON ANSWER ---
            // We want to find the MAXIMUM number of operations (pairs) we can make.
            // Let 'mid' be the number of pairs we want to test.
            
            // 'l' (Left) represents a number of pairs we KNOW is Possible.
            // Start at 0 because making 0 pairs is always possible.
            int l = 0;
            
            // 're' (Right/End) represents a number of pairs that is Impossible.
            // Start at n + 1 because we can't make more pairs than we have numbers.
            int re = n + 1;
            
            // Loop until l and re are adjacent. 
            // The answer will be 'l' because it's the largest "Possible" value found.
            while (re - l > 1) {
                // Calculate the midpoint (number of pairs to test)
                // Using bitwise shift >> 1 is the same as dividing by 2
                int mid = (l + re) >> 1;
                
                // Call the check function to see if 'mid' pairs are possible
                if (check(mid, q, r, k) == true) {
                    l = mid;   // mid works! It is a valid candidate. Try a larger number.
                } else {
                    re = mid;  // mid failed! It's too big. Limit the search range downwards.
                }
            }
            
            // Output the maximum number of operations found
            System.out.println(l);
        }
    }

    // --- STEP 3: THE CHECK FUNCTION ---
    // This function checks if it is possible to form EXACTLY 'mid' pairs
    // satisfying the condition without exceeding k.
    public static boolean check(int mid, int[] q, int[] r, long k) {
        
        // Loop to check each of the 'mid' pairs
        for (int i = 0; i < mid; i++) {
            
            // Get the i-th smallest number from q
            long qi = q[i];
            
            // --- GREEDY STRATEGY ---
            // To prevent the product from getting too large, we must balance the pairs.
            // We pair the Smallest available 'q' with the Largest available 'r'.
            // Since we are looking at a subset of size 'mid' (indices 0 to mid-1):
            // The largest 'r' in this subset is at index: mid - 1 - i
            long rj = r[mid - 1 - i];
            
            // --- MATH CONDITION ---
            // Derived formula: we can remove a pair if (q+1)(r+1) <= k + 1
            // We check the opposite: if product > k + 1, the condition is broken.
            // NOTE: Using 'long' for multiplication to prevent overflow.
            if (((qi + 1) * (rj + 1)) > k + 1) {
                return false; // Found a bad pair, so 'mid' operations are impossible
            }
        }
        
        // All pairs satisfied the condition
        return true;
    }
}
