# esper-realtimeaccess-mon-example
> Realtime event processing test project

# Esper 

## Event model

```java
public class AccessEvent {
  private String version;
  private String userHash;
  private String region;
  private String os;

  // setter/getter
}
```

```java
public class TimedRtaEvent {
	private String app;
	private long access;
	private Date timestamp;
	
	// setter/getter
```


### EPL

- 5秒毎にapp毎のos/region別のアクセス数を抽出する

```sql
  select app, os, region, count(distinct userHash) as val 
  from AccessEvent.win:time_batch(5 sec) 
  group by app, os, region
```

- 抽出されたアクセス数で、急にアクセス数が減少したappを検出する

```sql
select * from TimedRtaEvent 
  match_recognize ( 
    partition by app 
    measures A as access1, B as access2, C as access3 
    pattern (A B C)  
    define A as A.access > 100, 
           B as (A.access > B.access), 
           C as (B.access > C.access) and A.access > (C.access * 1.2))
```


# Test
`org.horiga.study.test.eventprocessing.esper.EsperTestMain`
