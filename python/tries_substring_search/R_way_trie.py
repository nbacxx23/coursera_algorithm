class TrieST(object):
    R = 256   ## extended ASCII
    def __init__(self):
        self.__n = 0
        self.__root = None
    
    def get(self,key):
        if key is None:
            print ("throw an exception")
        node_x = self.__get(self.__root,key,0)
        if node_x is None:
            return None
        return node_x.val
    
    def contains(self,key):
        if key is None:
            print ("throw an exception")
        return self.get(key) is not None
    
    def __get(self,node_x,key,d):
        if node_x is None:
            return None
        if d == len(key):
            return node_x
        return self.__get(node_x.next[ord(key[d])],key,d+1)
    
    def put(self,key,val):
        if key is None:
            print ("throw an exception")
        if val is None:
            self.delete(key)
        else:
            self.__root = self.__put(self.__root,key,val,0)
    
    def __put(self,node_x,key,val,d):
        if node_x is None:
            node_x = Node(self.R)
        if d==len(key):
            if node_x.val is None:
                self.__n += 1
            node_x.val = val
            return node_x
        node_x.next[ord(key[d])] = self.__put(node_x.next[ord(key[d])],key,val,d+1)
        return node_x
                 
    def size(self):
        return self.__n
    
    def is_empty(self):
        return self.size()==0
    
    def keys(self):
        return self.keys_with_prefix("")
    
    def keys_with_prefix(self,prefix):
        results = []
        node_x = self.__get(self.__root,prefix,0)
        self.__collect_prefix(node_x,prefix,results)
        return results
    
    def __collect_prefix(self,node_x,prefix,results):
        if node_x is None:
            return
        if node_x.val is not None:
            results.append(prefix)
        for c in range(self.R):
            prefix += chr(c)
            self.__collect_prefix(node_x.next[c],prefix,results)
            prefix = prefix[:-1]
            
    def keys_that_match(self,pattern):
        results = []
        self.__collect_match(self.__root,"",pattern,results)
        return results
            
    def __collect_match(self,node_x,prefix,pattern,results):
        if node_x is None:
            return
        d = len(prefix)
        if d == len(pattern) and node_x.val:
            results.append(prefix)
        if d == len(pattern):
            return
        c = pattern[d]
        if c == ".":
            for ch in range(self.R):
                prefix += chr(ch)
                self.__collect_match(node_x.next[ch],prefix,pattern,results)
                prefix = prefix[:-1]
        else:
            prefix += c
            self.__collect_match(node_x.next[ord(c)],prefix,pattern,results)
            prefix = prefix[:-1]
            
    def longest_prefix_of(self,query):
        if query is None:
            print ("argument to longestPrefixOf() is null")
        length = self.__longest_prefix_of(self.__root,query,0,-1)
        if length ==-1:
            return None
        else:
            return query[0:length]
        
    def __longest_prefix_of(self,node_x,query,d,length):
        if node_x is None:
            return length
        if node_x.val is not None:
            length = d
        if d == len(query):
            return length
        c = query[d]
        return self.__longest_prefix_of(node_x.next[ord(c)],query,d+1,length)
    
    def delete(self,key):
        if key is None:
            print ("argument to delete() is null")
        self.__root = self.__delete(self.__root,key,0)
        
    def __delete(self,node_x,key,d):
        if node_x is None:
            return None
        if d == len(key):
            if node_x.val:
                self.__n -= 1
            node_x.val = None
        else:
            c = key[d]
            node_x.next[ord(c)] = self.__delete(node_x.next[ord(c)],key,d+1)
        ## remove subtrie rooted at node_x if it is completely empty
        if node_x.val is not None:
            return node_x
        for c in range(self.R):
            if node_x.next[c]:
                return node_x
        return None
    
class Node(object):
    def __init__(self,R):
        self.val = None
        self.next = [None]*R