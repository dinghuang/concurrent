package com.example.demo.google;


import com.google.common.base.*;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.*;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * google 的guava使用demo
 *
 * @author dinghuang123@gmail.com
 * @see <a href="https://google.github.io/guava/releases/18.0/api/docs/">https://google.github.io/guava/releases/18.0/api/docs/</a>
 * @since 2018/5/28
 */
public class GuavaTest {

    /**
     * 不可变集合示例
     */
    public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of("red", "orange",
            "yellow", "green", "blue", "purple");


    /**
     * 通常来说，Guava Cache适用于：
     * <p>
     * 你愿意消耗一些内存空间来提升速度。
     * 你预料到某些键会被查询一次以上。
     * 缓存中存放的数据总量不会超出内存容量。（Guava Cache是单个应用运行时的本地缓存
     * 。它不把数据存放到文件或外部服务器。如果这不符合你的需求，请尝试Memcached这类工具）
     * 如果你的场景符合上述的每一条，Guava Cache就适合你。
     * https://github.com/google/guava/blob/68c8619d8ccd8811dd5408b828b7e3a45f07f21d/guava-tests/test/com/google/common/graph/GraphsTest.java
     */
    public void cacheTest() throws Exception {

    }

    /**
     * Ordering是Guava类库提供的一个犀利强大的比较器工具，
     * Guava的Ordering和JDK Comparator相比功能更强。它非常容易扩展，
     * 可以轻松构造复杂的comparator，然后用在容器的比较、排序等操作中。
     */
    public void orderingTest() {
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        //使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序 java8  Comparator.naturalOrder()
        Ordering<String> naturalOrdering = Ordering.natural();
        Comparator<String> stringComparable = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        };
        //从现有比较器返回一个
        Ordering<String> fromOrdering = Ordering.from(stringComparable);
        //从当前2个值，第一个参数为最小值，返回他们之间的比较器
        Ordering<Object> explicit = Ordering.explicit(list.get(0), list.get(1));
        //不排序,不改变原值赋值拷贝
        Ordering.allEqual().nullsLast().sortedCopy(list);
        //使用toString()返回的字符串按字典顺序进行排序 java8 Comparator.comparing(Object::toString)
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        //倒序 java8 Comparator.reversed()
        Ordering<Object> reverse = usingToStringOrdering.reverse();
        //返回将null视为小于所有其他值的排序，并使用它来比较非空值 java8  Comparator.nullsFirst(thisComparator)
        Ordering.allEqual().nullsFirst().sortedCopy(list);
        //返回将null视为大于所有其他值的顺序，并使用此顺序比较非空值 java8 Comparator.nullsLast(thisComparator)
        //Java 8 users: Use the lambda expression (a, b) -> 0 instead (in certain cases you may need to cast that to Comparator<YourType>)
        Ordering.allEqual().nullsLast().sortedCopy(list);
        //返回一个所有对象的任意顺序， 即compare(a, b) == 0 就是 a == b (identity equality)。
        // 本身的排序是没有任何含义， 但是在VM的生命周期是一个常量
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();
        //在F上返回一个新的排序顺序，首先对它们应用一个函数，然后用这个函数比较这些结果。
        //例如，要以字符串形式比较对象，以不区分大小写的方式使用 java8  Comparator.comparing(function, thisComparator)
        Ordering<Object> onResultOf = Ordering.from(String.CASE_INSENSITIVE_ORDER)
                .onResultOf(Functions.toStringFunction());
        //复合排序，第一个排序结束，可以用.compound()进行第二个排序,java8  Comparator.thenComparing(Comparator)
        Ordering<Object> compound = onResultOf.compound(arbitraryOrdering);
        //返回一个新的顺序，它通过两两比较相应的元素来排序迭代，直到找到一个非零的结果;强加“字典顺序”
        //如果达到一个迭代的结束，但不是另一个，则较短的迭代被认为小于较长的迭代。 java8  Comparators.lexicographical(Comparator)
        Ordering<Iterable<String>> o = Ordering.<String>natural().lexicographical();
        //根据此顺序返回指定值中的最小值。如果有多个最小值，则返回第一个值。 java8 Streams.stream(iterator).min(thisComparator).get()
        String min = naturalOrdering.min(list.iterator());
        String max = naturalOrdering.max(list.iterator());
        //按照此顺序返回给定迭代的k个最小元素，从最小到最大。如果存在少于k个元素，则将包括所有元素.
        // java8  Streams.stream(iterable).collect(Comparators.least(k, thisComparator)
        naturalOrdering.leastOf(list.iterator(), 5);
        //按照此顺序返回给定迭代的k个最大元素，从最小到最大。如果存在少于k个元素，则将包括所有元素.
        //java8  Streams.stream(iterable).collect(Comparators.greatest(k, thisComparator))
        naturalOrdering.greatestOf(list.iterator(), 5);
        //返回包含按此排序排序的元素的可变列表;只有当结果列表可能需要进一步修改时才使用它，
        // 或者可能包含null。输入未被修改。返回的列表是可序列化的并具有随机访问。
        //According to our benchmarking on Open JDK 7, immutableSortedCopy(java.lang.Iterable<E>) generally performs better
        // (in both time and space) than this method, and this method in turn generally performs better than copying
        // the list and calling Collections.sort(List).
        naturalOrdering.sortedCopy(list);
        //返回包含按此排序排序的元素的不可变列表。输入未被修改。
        //与Sets.newTreeSet（Iterable）不同，此方法不会根据比较器丢弃重复的元素。
        //执行的排序是稳定的，这意味着这些元素将按照它们出现在元素中的顺序出现在返回的列表中。
        naturalOrdering.immutableSortedCopy(list);
        //根据此顺序，如果每个元素在第一个元素之后的迭代中的每个元素大于或等于它之前的元素，则返回true。请注意，当迭代器少于两个元素时，总是如此。
        //java8  Comparators.isInOrder(Iterable, Comparator)
        Boolean a = naturalOrdering.isOrdered(list);
        //根据这个顺序，如果每个元素在第一个元素之后的迭代次数严格大于其之前的元素，则返回true。请注意，当迭代器少于两个元素时，总是如此。
        //java8  Comparators.isInStrictOrder(Iterable, Comparator)
        Boolean b = naturalOrdering.isStrictlyOrdered(list);
        //======================================================================
        System.out.println("naturalOrdering:" + naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:" + usingToStringOrdering.sortedCopy(list));
        System.out.println("arbitraryOrdering:" + arbitraryOrdering.sortedCopy(list));
        System.out.println("reverse:" + reverse.sortedCopy(list));
        System.out.println("onResultOf:" + onResultOf.sortedCopy(list));
        System.out.println("compound:" + compound.sortedCopy(list));

    }

    /**
     * 简化异常和错误的传播与检查
     * 有时候，你会想把捕获到的异常再次抛出。这种情况通常发生在Error或RuntimeException被捕获的时候，
     * 你没想捕获它们，但是声明捕获Throwable和Exception的时候，
     * 也包括了了Error或RuntimeException。Guava提供了若干方法，来判断异常类型并且重新传播异常
     */
    public void throwables() throws Exception {
        try {
        } catch (Throwable t) {
            Throwables.throwIfInstanceOf(t, IOException.class);
            Throwables.throwIfInstanceOf(t, SQLException.class);
            //返回throwable的最内层原因。链中的第一个throwable提供了最初检测到错误或异常时的上下文。用法示例：
            "Unable to assign a customer id".equals(Throwables.getRootCause(t).getMessage());
            //获取Throwable cause链作为列表。列表中的第一个条目将被抛出，然后是其原因层次结构。
            // 请注意，这是原因链的快照，并不会反映对原因链的任何后续更改。
            Iterables.filter(Throwables.getCausalChain(t), IOException.class);
            //getCauseAs 返回throwable的原因，转换为expectedCauseType。
            //返回一个包含toString（）结果的字符串，然后是throwable的完整递归堆栈跟踪
            // 。请注意，你可能不应该解析结果字符串;如果您需要编程访问堆栈帧，则可以调用Throwable.getStackTrace（）。
            t.getStackTrace();
        }
    }

    /**
     * 集合功能test
     * 1 不可变集合
     * 优点：当对象被不可信的库调用时，不可变形式是安全的；不可变对象被多个线程调用时，不存在竞态条件问题
     * 不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；
     * 不可变对象因为有固定不变，可以作为常量来安全使用。
     */
    public void collectTest() {
        //不可变集合示例
        List<User> users = new ArrayList<>();
        final ImmutableSet<User> USER_LIST = ImmutableSet.<User>builder()
                .addAll(users)
                .add(new User("XX", 1))
                .build();
        //此外，对有序不可变集合来说，排序是在构造集合的时候完成的
        ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
        //Multiset是什么？顾名思义，Multiset和Set的区别就是可以保存多个相同的对象。在JDK中，List和Set有一个基本的区别，
        // 就是List可以包含多个相同对象，且是有顺序的，而Set不能有重复，
        // 且不保证顺序（有些实现有顺序，例如LinkedHashSet和SortedSet等）
        // 所以Multiset占据了List和Set之间的一个灰色地带：允许重复，但是不保证顺序。
        ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz");
        //安全的拷贝
        //在可能的情况下避免线性拷贝，可以最大限度地减少防御性编程风格所带来的性能开销。
        ImmutableList<String> defensiveCopy = ImmutableList.copyOf(foobar);
        //Multiset
        User user = new User("XX", 1);
        users.add(user);
        Multiset<User> users1 = HashMultiset.create();
        users1.add(user);
        //给定元素在Multiset中的计数
        users1.count(user);
        //Multiset中不重复元素的集合，类型为Set<E>
        users1.elementSet();
        //和Map的entrySet类似，返回Set<Multiset.Entry<E>>，其中包含的Entry支持getElement()和getCount()方法
        users1.entrySet();
        //增加给定元素在Multiset中的计数
        users1.add(user, 1);
        //	减少给定元素在Multiset中的计数
        users1.remove(user, 1);
        //设置给定元素在Multiset中的计数，不可以为负数
        users1.setCount(user, 1);
        //返回集合元素的总个数（包括重复的元素）
        users1.size();
        //SortedMultiset是Multiset 接口的变种，它支持高效地获取指定范围的子集
        SortedMultiset<User> sortedMultiset = null;
        sortedMultiset.addAll(users);
        sortedMultiset.subMultiset(users.get(1), BoundType.CLOSED, users.get(100), BoundType.OPEN).size();
        //Guava的 Multimap可以很容易地把一个键映射到多个值。换句话说，Multimap是把键映射到任意多个值的一般方式。一般我们用Map<K, List<V>>
        // creates a ListMultimap with tree keys and array list values
        ListMultimap<String, User> treeListMultimap = MultimapBuilder.hashKeys().arrayListValues().build();
        treeListMultimap.put("XX", user);
        treeListMultimap.putAll("XX", users);
        //BiMap<K, V>是特殊的Map：可以用 inverse()反转BiMap<K, V>的键值映射保证值是唯一的，因此 values()返回Set而不是普通的Collection
        BiMap<String, Integer> userId = HashBiMap.create();
        String userForId = userId.inverse().get(1);
        //通常来说，当你想使用多个键做索引的时候，你可能会用类似Map<FirstName, Map<LastName, Person>>的实现，
        // 这种方式很丑陋，使用上也不友好。Guava为此提供了新集合类型Table，它有两个支持所有类型的键：”行”和”列”
        Table<Integer, Integer, User> weightedGraph = HashBasedTable.create();
        weightedGraph.put(1, 2, user);
        weightedGraph.put(1, 3, user);
        //ClassToInstanceMap是一种特殊的Map：它的键是类型，而值是符合键所指类型的对象。
        ClassToInstanceMap<Number> numberDefaults = MutableClassToInstanceMap.create();
        numberDefaults.putInstance(Integer.class, Integer.valueOf(0));
        //RangeSet描述了一组不相连的、非空的区间。当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略。
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}
        //RangeSet最基本的操作，判断RangeSet中是否有任何区间包含给定元素。
        rangeSet.contains(1);
        //返回包含给定元素的区间；若没有这样的区间，则返回null。
        rangeSet.rangeContaining(11);
        //简单明了，判断RangeSet中是否有任何区间包括给定区间。
        rangeSet.encloses(Range.open(5, 10));
        //返回包括RangeSet中所有区间的最小区间。
        rangeSet.span();
        //RangeMap描述了”不相交的、非空的区间”到特定值的映射。和RangeSet不同，RangeMap不会合并相邻的映射，即便相邻的区间映射到相同的值
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}
    }

    /**
     * 集合工具类
     */
    public void collectionUtil() {
        //Guava提供了能够推断范型的静态工厂方法：
        List<User> list = Lists.newArrayList();
        Map<String, User> map = Maps.newLinkedHashMap();
        //用工厂方法模式，可以方便地在初始化时就指定起始元素
        Set<String> copySet = Sets.newHashSet("XX");
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
        //通过为工厂方法命名（Effective Java第一条），可以提高集合初始化大小的可读性
        List<String> exactly100 = Lists.newArrayListWithCapacity(100);
        List<String> approx100 = Lists.newArrayListWithExpectedSize(100);
        Set<String> approx100Set = Sets.newHashSetWithExpectedSize(100);
        //Guava引入的新集合类型没有暴露原始构造器，也没有在工具类中提供初始化方法。而是直接在集合类中提供了静态工厂方法
        Multiset<String> multiset = HashMultiset.create();
        //串联多个iterables的懒视图
        // concatenated包括元素 1, 2, 3, 4, 5, 6
        Iterable<Integer> concatenated = Iterables.concat(Ints.asList(1, 2, 3), Ints.asList(4, 5, 6));
        //获取iterable中唯一的元素，如果iterable为空或有多个元素，则快速失败
        String theElement = Iterables.getOnlyElement(approx100);
        //返回对象在iterable中出现的次数
        Iterables.frequency(approx100, String.class);
        //把iterable按指定大小分割，得到的子集都不能进行修改操作
        Iterables.partition(approx100, 1);
        //返回iterable的第一个元素，若iterable为空则返回默认值
        Iterables.getFirst(approx100, String.class);
        //返回iterable的最后一个元素，若iterable为空则抛出NoSuchElementException
        String lastAdded = Iterables.getLast(approx100);
        //如果两个iterable中的所有元素相等且顺序一致，返回true
        Iterables.elementsEqual(approx100, approx100);
        //返回iterable的不可变视图
        Iterables.unmodifiableIterable(approx100);
        //限制iterable的元素个数限制给定值
        Iterables.limit(approx100, 1);
        //==========================java中的对比==================
        List<String> javaResults = list.stream()
                .filter(User::isAgeEquleZero)
                .map(Object::toString)
                .limit(10)
                .collect(Collectors.toList());
        ImmutableList<String> results =
                FluentIterable.from(list)
                        .filter(User::isAgeEquleZero)
                        .transform(Object::toString)
                        .limit(10)
                        .toList();
        //==========================
        list.contains(new User());
        FluentIterable.from(list).contains(new User());
        //===========================
        list.size();
        FluentIterable.from(list).size();
        //还有很多，基本都有，自己去找，同时还提供把自己变成不可变集合
        FluentIterable.from(list).toSet();
        FluentIterable.from(list).toMap(User::getAge);
        FluentIterable.from(list).toMultiset();
        FluentIterable.from(list).toSortedSet((o1, o2) -> 0);
        List countUp = Ints.asList(1, 2, 3, 4, 5);
        List countDown = Lists.reverse(countUp);
        List<List> parts = Lists.partition(countUp, 2);
        //set工具方法
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        // intersection包含"two", "three", "seven"
        intersection.immutableCopy();
        //可以使用交集，但不可变拷贝的读取效率更高
        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");
        Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
        // {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
        //  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}
        Set<Set<String>> animalSets = Sets.powerSet(animals);
        //Maps.uniqueIndex(Iterable,Function)通常针对的场景是：有一组对象，它们在某个属性上分别有独一无二的值，而我们希望能够按照这
        // 个属性值查找对象——译者注：这个方法返回一个Map，键为Function返回的属性值，
        // 值为Iterable中相应的元素，因此我们可以反复用这个Map进行查找操作。
        //比方说，我们有一堆字符串，这些字符串的长度都是独一无二的，而我们希望能够按照特定长度查找字符串
        ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(Lists.asList("ab", "a", new String[]{"avb"}),
                (Function<String, Integer>) string -> string.length());
        //difference
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        MapDifference<String, Integer> diff = Maps.difference(left, right);
        diff.entriesInCommon();
        diff.entriesInCommon();
        diff.entriesOnlyOnLeft();
        diff.entriesOnlyOnRight();
        //处理BiMap的工具方法
        Collections.synchronizedMap(left);
        Collections.unmodifiableMap(left);
        //标准的Collection操作会忽略Multiset重复元素的个数，而只关心元素是否存在于Multiset中，
        // 如containsAll方法。为此，Multisets提供了若干方法，以顾及Multiset元素的重复性：
        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.add("a", 2);
        Multiset<String> multiset2 = HashMultiset.create();
        multiset2.add("a", 5);
        //返回true；因为包含了所有不重复元素，
        multiset1.containsAll(multiset2);
        //虽然multiset1实际上包含2个"a"，而multiset2包含5个"a"
        // returns false
        Multisets.containsOccurrences(multiset1, multiset2);
        //multiset2移除所有"a"，虽然multiset1只有2个"a"
        multiset2.removeAll(multiset1);
        // returns true
        multiset2.isEmpty();
        //返回Multiset的不可变拷贝，并将元素按重复出现的次数做降序排列
        Multiset<String> multisets = HashMultiset.create();
        multisets.add("a", 3);
        multisets.add("b", 5);
        multisets.add("c", 1);
        ImmutableMultiset highestCountFirst = Multisets.copyHighestCountFirst(multisets);
        //字符串按长度分组
        ImmutableSet digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        Function<String, Integer> lengthFunction = string -> string.length();
        ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(digits, lengthFunction);
        //鉴于Multimap可以把多个键映射到同一个值（译者注：实际上这是任何map都有的特性），
        // 也可以把一个键映射到多个值，反转Multimap也会很有用。Guava 提供了invertFrom(Multimap toInvert,Multimap dest)做这个操作
        // ，并且你可以自由选择反转后的Multimap实现。
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));
        // 使用LinkedHashMaps替代HashMaps
        Table<String, Character, Integer> table = Tables.newCustomTable(
                Maps.<String, Map<Character, Integer>>newLinkedHashMap(),
                () -> Maps.newLinkedHashMap());
        //方法允许你把Table<C, R, V>转置成Table<R, C, V>。例如，如果你在用Table构建加权有向图，这个方法就可以把有向图反转
        Tables.transpose(table);
        //复制一个List，并去除连续的重复元素。
        List<String> result = Lists.newArrayList();
        PeekingIterator<String> iter = Iterators.peekingIterator(result.iterator());
        while (iter.hasNext()) {
            String current = iter.next();
            while (iter.hasNext() && iter.peek().equals(current)) {
                //跳过重复的元素
                iter.next();
            }
            result.add(current);
        }
        //包装一个iterator以跳过空值
        skipNulls(digits.iterator());
    }

    public static Iterator<String> skipNulls(final Iterator<String> in) {
        return new AbstractIterator<String>() {
            @Override
            protected String computeNext() {
                while (in.hasNext()) {
                    String s = in.next();
                    if (s != null) {
                        return s;
                    }
                }
                return endOfData();
            }
        };
    }


    public static class User implements Comparable<User> {

        private String name;
        private Integer age;

        private User() {
        }

        private User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        /**
         * guava比较器
         *
         * @param other other
         * @return int
         */
        @Override
        public int compareTo(User other) {
            return ComparisonChain.start()
                    .compare(name, other.name)
                    //natural()	对可排序类型做自然排序，如数字按大小，日期按先后排序
                    //usingToString()	按对象的字符串形式做字典排序[lexicographical ordering]
                    //from(Comparator)	把给定的Comparator转化为排序器
                    //实现自定义的排序器时，除了用上面的from方法，也可以跳过实现Comparator，而直接继承Ordering
                    //地址：https://google.github.io/guava/releases/snapshot-jre/api/docs/
                    .compare(age, other.age, Ordering.natural().nullsLast())
                    .result();
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .addValue(name)
                    .addValue(age)
                    .toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, age);
        }

        public static Boolean isAgeEquleZero(User user) {
            return user.getAge() != 0;
        }
    }
}
