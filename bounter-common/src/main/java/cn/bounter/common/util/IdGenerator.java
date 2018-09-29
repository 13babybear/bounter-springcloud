package cn.bounter.common.util;

import cn.bounter.common.model.Sequence;

/**
 * 分布式id生成器
 * @author simon 2017/11/2
 *
 */
public class IdGenerator {

	private static final Sequence worker = new Sequence();

    public static Long getId() {
        return worker.nextId();
    }

    public static void main(String[] args) {
    	for(int i=0; i<10; i++) {
    		System.out.println(getId());
    	}
    }
}
