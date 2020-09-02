import random
class Quick3Way():
    def __init__(self):
        pass
    
    def sort(self,a):
        random.shuffle(a)
        self.__sort(a,0,len(a)-1)
        assert self.is_sorted(a)
    
    # quicksort the subarray a[lo .. hi] using 3-way partitioning
    def __sort(self,a,lo,hi):
        if hi <= lo:
            return
        
        lt = lo
        gt = hi
        v = a[lo]
        i = lo + 1
        while i <= gt:
            if a[i]<v:
                a[lt],a[i] = a[i],a[lt]
                i += 1
                lt += 1
            elif a[i]>v:
                a[i],a[gt] = a[gt],a[i]
                gt -= 1
            else:
                i += 1
        
        # a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        self.__sort(a, lo, lt-1)
        self.__sort(a, gt+1, hi)
        assert self.__is_sorted(a, lo, hi)
        
    # is v < w ?
    @staticmethod
    def __less(v,w):
        return v < w
    
    def is_sorted(self,a):
        return self.__is_sorted(a,0,len(a)-1)
    
    def __is_sorted(self,a,lo,hi):
        i = lo +1
        while i<=hi:
            if self.__less(a[i],a[i-1]):
                return False
            i += 1
        return True