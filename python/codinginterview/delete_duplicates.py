from codinginterview.shared.node import Node
from codinginterview.shared.linked_list import LinkedList


def delete_duplicates(linkedlist):
    current = linkedlist.node
    while current is not None:
        runner = current
        while runner.next is not None:
            if runner.next.data == current.data:
                runner.next = runner.next.next
            else:
                runner = runner.next
        current = current.next


linked = LinkedList()
linked.add(Node(1))
linked.add(Node(2))
linked.add(Node(2))
linked.add(Node(2))
linked.add(Node(3))
linked.add(Node(4))
linked.add(Node(5))
linked.add(Node(1))
linked.add(Node(6))
linked.add(Node(7))
delete_duplicates(linked)
linked.print()