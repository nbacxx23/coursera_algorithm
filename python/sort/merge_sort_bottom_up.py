class MergeBottomUp():
    def __init__(self):
        pass
    
    @staticmethod
    def __less(v,w):
        return v<w
    
    def __is_sorted(self,a,lo,hi):
        i = lo + 1
        while i<=hi:
            if self.__less(a[i],a[i-1]):
                return False
            i += 1
        return True
    
    def __merge(self,a,aux,lo,mid,hi):
        k = lo
        while k <= hi:
            aux[k] = a[k]
            k += 1
        
        i = lo
        j = mid + 1
        k = lo
        while k<=hi:
            if i > mid:
                a[k] = aux[j]
                j += 1
            elif j > hi:
                a[k] = aux[i]
                i += 1
            elif self.__less(aux[j],aux[i]):
                a[k] = aux[j]
                j += 1
            else:
                a[k] = aux[i]
                i += 1
            k += 1
            
    def sort(self,a):
        n = len(a)
        aux = [None]*n
        length = 1
        while length < n:
            lo = 0
            while lo < n - length:
                mid = lo + length - 1
                hi = min(lo+length+length-1,n-1)
                self.__merge(a,aux,lo,mid,hi)
                lo += length+length
            length *= 2
        
        assert self.is_sorted(a)
        
    def is_sorted(self,a):
        return self.__is_sorted(a, 0, len(a) - 1)