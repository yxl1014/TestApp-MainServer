package main.ly.config.LruBean;


import main.logs.LogMsg;
import main.logs.LogUtil;
import main.logs.OptionDetails;
import org.apache.logging.log4j.Logger;

public class LruUtil<T> {
    private int maxSize;

    public LruUtil(int maxSize) {
        this.maxSize = maxSize;
    }


//    public void setMaxSize(int maxSize) {
//        this.maxSize = maxSize;
//    }

    class Node {
        int key;
        T value;
        Node pre;
        Node next;

        public Node(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    class DoubleList {
        private Node head;// 头节点
        private Node tail;// 尾节点
        private int length;

        public DoubleList() {
            head = new Node(-1,null);
            tail = head;
            length = 0;
        }

        void add(Node teamNode)//从尾巴插入
        {
            tail.next = teamNode;
            teamNode.pre = tail;
            tail = teamNode;
            length++;
        }

        Node findByKey(int key){
            Node temp=tail;
            while(temp.key!=key){
                if(temp.pre==head){
                    return null;
                }
                temp=temp.pre;
            }
            return temp;
        }

        void deleteFirst() {
            if (head.next == null)
                return;
            if (head.next == tail)
                tail = head;
            head.next = head.next.next;

            if (head.next != null)
                head.next.pre = head;
            length--;
        }

        void deleteNode(Node temp) {
            if(temp==null){
                return ;
            }
            temp.pre.next = temp.next;
            if (temp.next != null)
                temp.next.pre = temp.pre;
            if (temp == tail)
                tail = tail.pre;
            temp.pre = null;
            temp.next = null;
            length--;
        }

        public String toString() {
            Node temp = head.next;
            String disString = "len:" + length + " ";
            while (temp != null) {
                disString += "key:" + temp.key + " val:" + temp.value + " ";
                temp = temp.next;
            }
            return disString;
        }
    }

    DoubleList doubleList=new DoubleList();
    private static final Logger logger = LogUtil.getLogger(LruUtil.class);

    /**
     * 打印函数
     */
    public void dis() {
        System.out.println("listLength:" + doubleList.length + " " + doubleList+ " maxSize:" + maxSize);
    }

    /**
     * 通过任务Id查找
     * @param key
     * @return
     */
    public T get(int key) {
        Node node;
        node = doubleList.findByKey(key);
        if (node==null) {
            logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.LRU_GET_VALUE_NULL));
            return null;
        }
        doubleList.deleteNode(node);
        doubleList.add(node);
        logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.LRU_GET_VALUE_OK));
        return node.value;
    }


    /**
     * 存放任务id和对象
     * @param key
     * @param value
     * @return
     */
    public boolean put(int key, T value) {
        Node deleteNode = doubleList.findByKey(key);
        if (deleteNode == null && doubleList.length == maxSize) {
            doubleList.deleteFirst();
        }
        doubleList.deleteNode(deleteNode);
        Node node = new Node(key, value);
        doubleList.add(node);
        logger.info(LogUtil.makeOptionDetails(LogMsg.UTIL, OptionDetails.LRU_PUT_TASK_OK));
        return true;
    }
}
