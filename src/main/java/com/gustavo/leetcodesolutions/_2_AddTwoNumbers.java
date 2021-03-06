package com.gustavo.leetcodesolutions;


/**
 * Created by gustaov on 2018/9/2.
 */
public class _2_AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        ListNode head = new ListNode(0);
        int value = l1.val + l2.val;
        int toUp = value / 10;
        value = value % 10;
        ListNode v = new ListNode(value);
        head.next = v;
        l1= l1.next;l2=l2.next;
        while (l1 != null || l2 != null) {
            int v1 = toUp;
            if (l1 != null) {
                v1 += l1.val;
            }
            if (l2 != null)
                v1 += l2.val;
            toUp = v1/10;
            v1 = v1%10;
            ListNode ln = new ListNode(v1);
            v.next = ln;
            v=ln;
            l1=l1.next;l2=l2.next;
        }
        return head.next;
    }

    public static void main(String[] args) {

        _2_AddTwoNumbers addTwoNumbers  = new _2_AddTwoNumbers();
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        node1.next=node2;
        node2.next = node3;

        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        ListNode node6 = new ListNode(4);
        node4.next=node5;
        node5.next=node6;

        ListNode result = addTwoNumbers.addTwoNumbers(node1,node4);
        while(result!=null) {
            System.out.println(result.val);
            result= result.next;
        }
    }
}

