class Heap():
    def __init__(self):
        pass
    
    def sort(self,pq):
        n = len(pq)
        
        # heapify phase
        k = n//2
        for i in range(k,0,-1):
            self.__sink(pq,i,n)
            
        # sortdown phase
        k = n
        while k > 1:
            self.__exch(pq,1,k)
            k -= 1
            self.__sink(pq,1,k)
    
    
    #***************************************************************************
    #* Helper functions to restore the heap invariant.
    #***************************************************************************/
    def __sink(self,pq,k,n):
        while 2*k <= n:
            j = 2*k
            if j<n and self.__less(pq,j,j+1):
                j += 1
            if not self.__less(pq,k,j):
                break
            self.__exch(pq,k,j)
            k = j
            
    def __exch(self,pq,i,j):
        pq[i-1],pq[j-1] = pq[j-1],pq[i-1]
        
    def __less(self,pq,i,j):
        return pq[i-1]<pq[j-1]
    
    def show(self,a):
        for i in range(len(a)):
            print (a[i])