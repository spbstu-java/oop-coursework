package lab2;

import java.util.List;
import java.util.Set;
import java.util.Map;

public class AnnotatedClass {
//	@MyAnnotation(parameter=2)
	public void pub_method_1(short a1, byte a2) {
		System.out.println(String.format("Public method 1: %d", a1%a2));
	}
	@MyAnnotation(parameter=5)
	public void pub_method_2(char a1) {
		System.out.println("Public method 2: '"+a1+"'");
	}
	@MyAnnotation(parameter=2)
	public void pub_method_3() {
		System.out.println("Public method 3");
	}
//	@MyAnnotation(parameter=2)
	protected void pro_method_1(int arg1, long arg2) {
		System.out.println(String.format("Protected method 1: (%d) + (%d) = (%d)", arg1, arg2, arg1+arg2));
	}
	@MyAnnotation(parameter=2)
	protected void pro_method_2(int[] arg1) {
		int sum = 0;
		for(int i=0; i<arg1.length; ++i)
			sum += arg1[i];
		System.out.println(String.format("Protected method 2: sum = %d", sum));
	}
	@MyAnnotation(parameter=2)
	protected void pro_method_3(Set<Object> arg1) {
		System.out.println("Protected method 3: ");
		arg1.forEach(s->System.out.println(s));
	}
	@MyAnnotation(parameter=1)
	private void pri_method_1(List<String> arg1, String arg2) {
		System.out.println("Private method 1: "+String.join(arg2, arg1));
	}
	@MyAnnotation(parameter=2)
	private void pri_method_2(Map<Object, Object> map) {
		System.out.println("Private method 2: " + map.toString());
	}
	@MyAnnotation(parameter=7)
	private void pri_method_3(Boolean arg1, float arg2, double arg3) {
		System.out.println(String.format("Private method 3: %f", (arg1? arg2 :arg3)));
	}
}
