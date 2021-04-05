### Elementary Symbol Tables

1 Java autoboxing and equals(). Consider  two \mathtt{double}double values \mathtt{a}a and \mathtt{b}b and their corresponding \mathtt{Double}Double values \mathtt{x}x and \mathtt{y}y.

Find values such that \mathtt{(a == b)}(a==b) is \mathtt{true}true but \mathtt{x.equals(y)}x.equals(y) is \mathtt{false}false.
Find values such that \mathtt{(a == b)}(a==b) is \mathtt{false}false but \mathtt{x.equals(y)}x.equals(y) is \mathtt{true}true.

Hint: IEEE floating point arithmetic has some peculiar rules for \mathtt{0.0}0.0, \mathtt{-0.0}−0.0, and \mathtt{NaN}NaN. Java requires that \mathtt{equals()}equals() implements an equivalence relation.


2 Check if a binary tree is a BST. Given a binary tree where each \mathtt{Node}Node contains a key, determine whether it is a binary search tree. Use extra space proportional to the height of the tree.

Correct
Hint: design a recursive function \mathtt{isBST(Node x, Key min, Key max)}isBST(Nodex,Keymin,Keymax) that determines whether \mathtt{x}x is the root of a binary search tree with all keys between \mathtt{min}min and \mathtt{max}max.

3 Inorder traversal with constant extra space. Design an algorithm to perform an inorder traversal of a binary search tree using only a constant amount of extra space.

Hint: you may modify the BST during the traversal provided you restore it upon completion.


4 Web tracking. Suppose that you are tracking nn web sites and mm users and you want to support the following API:

User visits a website.
How many times has a given user visited a given site?
What data structure or data structures would you use?

Hint: maintain a symbol table of symbol tables.


### Balanced Search Trees
1 Red–black BST with no extra memory. Describe how to save the memory for storing the color information when implementing a red–black BST. 

Hint: modify the structure of the BST to encode the color information.

2 Document search. Design an algorithm that takes a sequence of n document words and a sequence of m query words and find the shortest interval in which the m query words appear in the document in the order given. The length of an interval is the number of words in that interval.

Hint: for each word, maintain a sorted list of the indices in the document in which that word appears. Scan through the sorted lists of the query words in a judicious manner.

3 Generalized queue. Design a generalized queue data type that supports all of the following operations in logarithmic time (or better) in the worst case.

Create an empty data structure.
Append an item to the end of the queue.
Remove an item from the front of the queue.
Return the ith item in the queue.
Remove the ith item from the queue.

Hint: create a red–black BST where the keys are integers and the values are the items such that the i^{th}ith largest integer key in the red–black BST corresponds to the i^{th}ith item in the queue.