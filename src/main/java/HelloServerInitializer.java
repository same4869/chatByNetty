import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化起，channel注册后，会执行里面的相应初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketchannel去获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //通过管道，添加handler
        //HttpServerCodec是有netty自己提供的助手类，可以理解为拦截器
        //当请求到服务器，我们需要做解码，相应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        //添加自己定义的助手类，返回"hello netty"
        pipeline.addLast("customHandler", new CustomHandler());

    }
}
