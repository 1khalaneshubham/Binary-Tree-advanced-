import java.util.ArrayList;

public class KthAncestorOfNode {
    
       static class  Node {
        int data;
        Node left,right;

        public Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    // calculate hight of tree
    public static int hight(Node root){
        if(root == null){
            return 0;
        }

        int lh = hight(root.left);
        int rh = hight(root.right);
        return Math.max(lh,rh)+1;
    }
    // Count of Node
    public static int Count(Node root){
        if(root == null){
            return 0;
        }
        
        int leftCount = Count(root.left);
        int rightCount = Count(root.right);
        
        return leftCount + rightCount + 1;
    }
    // Sum of Nodes
    public static int sum(Node root){
        if(root == null){
            return 0;
        }
        int leftSum = sum(root.left);
        int rightSum = sum(root.right);
        return leftSum + rightSum + root.data;
    }

    // Diameter of Tree
    // ha jast used kaycha nahi bahava yachi TC jast aahe 
    // ya peksha better approach aahe to kahli dila aahe to used kary cha
    // TC = O(n^2)
    public static int diameter1(Node root){
        if(root == null){
            return 0;
        }
        int leftDiam = diameter1(root.left);
        int leftHt = hight(root.left);
        int rightDiam = diameter1(root.right);
        int rightHt = hight(root.right);

        int selfDiam = leftHt + rightHt + 1;
        
        return Math.max(selfDiam, Math.max(leftDiam, rightDiam));
    }

    static class Info {
        int diam;
        int ht;

        public Info(int diam,int ht){
            this.diam = diam;
            this.ht = ht;
        }
    }
    public static Info diameter2(Node root){ // TC = O(n)
        if(root == null){
            return new Info(0, 0);
        }
        Info leftInfo = diameter2(root.left);
        Info rightInfo = diameter2(root.right);

        int diam = Math.max(Math.max(leftInfo.diam, rightInfo.diam),leftInfo.ht + rightInfo.ht + 1);
        int ht = Math.max(leftInfo.ht, rightInfo.ht) + 1;

        return new Info(diam, ht);
    }

    public static boolean isIdentical(Node node,Node subRoot){
        if(node == null && subRoot == null){
            return true;
        }else if(node == null || subRoot == null || node.data != subRoot.data){
            return false;
        }

        if(!isIdentical(node.left, subRoot.left)){
            return false;
        }
        if(!isIdentical(node.right, subRoot.right)){
            return false;
        }
        return true;
    }
    public static boolean isSubtree(Node root,Node subRoot){

        if(root == null){
            return false;
        }
        if(root.data == subRoot.data){
            if(isIdentical(root,subRoot)){
                return true;
            }
        }

       return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    public static void KLevel(Node root, int level, int k){
        if(root == null){
            return;
        }
        if(level == k){
            System.out.print(root.data+" ");
            return;
        }

        KLevel(root.left, level+1, k);
        KLevel(root.right, level+1, k);
    }
    // Approach 1
    // TC = O(n)
    public static boolean getPath(Node root, int n,ArrayList<Node> path){
        if(root == null){
            return false;
        }
        path.add(root);

        if(root.data == n){
            return true;
        }

        boolean foundLeft = getPath(root.left, n, path);
        boolean foundRight = getPath(root.right, n, path);

        if(foundLeft || foundRight){
            return true;
        }

        path.remove(path.size()-1);
        return false;
    }
    public static Node lca(Node root ,int n1,int n2){
        ArrayList<Node> path1 = new ArrayList<>();
        ArrayList<Node> path2 = new ArrayList<>();

        getPath(root,n1,path1);
        getPath(root,n2,path2);

        // last common ancestor 
        int i = 0;
        for(; i<path1.size() && i< path2.size(); i++){
            if(path1.get(i) != path2.get(i)){
                break;
            }
        }

        // last equal node -> i-1th
        Node lca = path1.get(i-1);
        return lca;
    }

    // Approach 2
    public static Node lca2(Node root, int n1, int n2){

        if(root == null || root.data == n1 || root.data == n2){
            return root;
        }

        Node leftLca = lca2(root.left, n1, n2);
        Node rightLca = lca2(root.right, n1, n2);

        // leftLca = val rightLca = null
        if(rightLca == null){
            return leftLca;
        }
        if(leftLca == null){
            return rightLca;
        }

        return root;
    }
    // min Distance Betn Node
    public static int lcaDist(Node root, int n){
        if(root == null){
            return -1;
        }
        if(root.data == n){
            return 0;
        }
        int leftDist = lcaDist(root.left, n);
        int rightDist = lcaDist(root.right, n);

        if(leftDist == -1 && rightDist == -1){
            return -1;
        }else if(leftDist == -1){
            return rightDist+1;
        }else{
            return leftDist+1;
        }
    }

    public static int MinDist(Node root, int n1,int n2){
        Node lca = lca2(root, n1, n2);
        int dist1 = lcaDist(lca,n1);
        int dist2 = lcaDist(lca,n2);

        return dist1 + dist2;
    }
// Kth Ancestor
    public static int KAncestor(Node root,int n, int k){
       
        if(root == null){
            return -1;
        }

        if(root.data == n){
            return 0;
        }

        int leftDist = KAncestor(root.left, n, k);
        int rightDist = KAncestor(root.right, n, k);

        if(leftDist == -1 && rightDist == -1){
            return -1;
        }

        int max = Math.max(leftDist, rightDist);
        if(max+1 == k){
            System.out.println(root.data);
        }
        return max+1;

    }
    public static void main(String[] args) {
        /*
                   1 
                  / \
                2    3
               / \  / \
              4  5  6  7
        */  
        
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

       int n =5  , k = 2;
       KAncestor(root, n, k);

    }
}
