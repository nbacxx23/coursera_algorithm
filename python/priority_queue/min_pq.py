class MinPQ():
    def __init__(self,init_capacity):
        self.pq = [None]*(init_capacity+1)
        self.n = 0
    
    # returns true if this priority queue is empty
    def is_empty(self):
        return self.n==0
    
    # returns the number of keys on this priority queue
    def size(self):
        return self.n
    
    def get_min(self):
        if self.is_empty():
            raise "priority queue underflow"
        return self.pq[1]
    
    def __resize(self,capacity):
        assert capacity > self.n
        temp = [None]*(capacity)
        for i in range(1,self.n+1):
            temp[i] = self.pq[i]
        self.pq = temp
        
    def insert(self,x):
        # double size of array if necessary
        if self.n==(len(self.pq)-1):
            self.__resize(2*len(self.pq))
        
        #print (self.n,len(self.pq)-1,2*len(self.pq))
        # add x, and percolate it up to maintain heap invariant
        self.n = self.n + 1
        self.pq[self.n] = x
        self.__swim(self.n)
        assert self.__is_min_heap()
        
    def del_min(self):
        if self.is_empty():
            raise "priority queue underflow"
        min_key = self.pq[1]
        self.__exch(1,self.n)
        self.n = self.n - 1
        self.__sink(1)
        ## to avoid loitering and help with garbage collection
        self.pq[self.n+1] = None
        if (self.n>0 and self.n==(len(self.pq)-1)//4):
            self.__resize(len(self.pq)//2)
        assert self.__is_min_heap()
        return min_key
    
    #/***************************************************************************
    # Helper functions to restore the heap invariant.
    #**************************************************************************/
    
    def __swim(self,k):
        while k>1 and self.__less(k,k//2):
            self.__exch(k,k//2)
            k = k//2
            
    def __sink(self,k):
        while 2*k <=self.n:
            j = 2*k
            if j<self.n and self.__less(j+1,j):
                j += 1
            if not self.__less(j,k):
                break
            self.__exch(k,j)
            k = j
            
    def __less(self,i,j):
        return self.pq[i] < self.pq[j]
    
    def __exch(self,i,j):
        self.pq[i],self.pq[j] = self.pq[j],self.pq[i]
    
    def __is_min_heap(self):
        for i in range(1,self.n+1):
            if self.pq[i] == None:
                return False
        for i in range(self.n+1,len(self.pq)):
            if self.pq[i] != None:
                return False
        if self.pq[0] != None:
            return False
        return self.__is_min_heap_ordered(1)
    
    def __is_min_heap_ordered(self,k):
        if k > self.n:
            return True
        left = 2*k
        right = 2*k + 1
        if left <= self.n and self.__less(left,k):
            return False
        if right <= self.n and self.__less(right,k):
            return False
        return self.__is_min_heap_ordered(left) and self.__is_min_heap_ordered(right)