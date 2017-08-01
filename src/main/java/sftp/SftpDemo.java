package sftp;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * sftp���ߡ�ע�⣺���췽�����������ֱ��ǻ���������֤��������Կ��֤��
 *
 * @see http://xliangwu.iteye.com/blog/1499764
 * @author Somnus
 */
public class SftpDemo {
    private transient Logger log = LoggerFactory.getLogger(this.getClass());

    private ChannelSftp sftp;

    private Session session;
    /** FTP ��¼�û���*/
    private String username;
    /** FTP ��¼����*/
    private String password;
    /** ˽Կ�ļ���·��*/
    private String keyFilePath;
    /** FTP ��������ַIP��ַ*/
    private String host;
    /** FTP �˿�*/
    private int port;


    /**
     * �������������֤��sftp����
     * @param userName
     * @param password
     * @param host
     * @param port
     */
    public SftpDemo(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * ���������Կ��֤��sftp����
     * @param userName
     * @param host
     * @param port
     * @param keyFilePath
     */
    public SftpDemo(String username, String host, int port, String keyFilePath) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.keyFilePath = keyFilePath;
    }

    public SftpDemo(){}

    /**
     * ����sftp������
     *
     * @throws Exception
     */
    public void login() throws Exception {
        try {
            JSch jsch = new JSch();
            if (keyFilePath != null) {
                jsch.addIdentity(keyFilePath);// ����˽Կ
                log.info("sftp connect,path of private key file��{}" , keyFilePath);
            }
            log.info("sftp connect by host:{} username:{}",host,username);

            session = jsch.getSession(username, host, port);
            log.info("Session is build");
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.setTimeout(5000);//5��
            session.connect();
            log.info("Session is connected");

            Channel channel = session.openChannel("sftp");
            channel.connect();
            log.info("channel is connected");

            sftp = (ChannelSftp) channel;
            log.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));
        } catch (JSchException e) {
            log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});
            throw new Exception(e.getMessage(),e);
        }
    }

    /**
     * �ر����� server
     */
    public void logout(){
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                log.info("sftp is closed already");
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * ���������������ϴ���sftp��Ϊ�ļ�
     *
     * @param directory
     *            �ϴ�����Ŀ¼
     * @param sftpFileName
     *            sftp���ļ���
     * @param in
     *            ������
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException{
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.warn("directory is not exist");
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);
        log.info("file:{} is upload successful" , sftpFileName);
    }

    /**
     * �ϴ������ļ�
     *
     * @param directory
     *            �ϴ���sftpĿ¼
     * @param uploadFile
     *            Ҫ�ϴ����ļ�,����·��
     * @throws FileNotFoundException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException{
        File file = new File(uploadFile);
        upload(directory, file.getName(), new FileInputStream(file));
    }

    /**
     * ��byte[]�ϴ���sftp����Ϊ�ļ���ע��:��String����byte[]�ǣ�Ҫָ���ַ�����
     *
     * @param directory
     *            �ϴ���sftpĿ¼
     * @param sftpFileName
     *            �ļ���sftp�˵�����
     * @param byteArr
     *            Ҫ�ϴ����ֽ�����
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException{
        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
    }

    /**
     * ���ַ�������ָ�����ַ������ϴ���sftp
     *
     * @param directory
     *            �ϴ���sftpĿ¼
     * @param sftpFileName
     *            �ļ���sftp�˵�����
     * @param dataStr
     *            ���ϴ�������
     * @param charsetName
     *            sftp�ϵ��ļ��������ַ����뱣��
     * @throws UnsupportedEncodingException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException{
        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));

    }

    /**
     * �����ļ�
     *
     * @param directory
     *            ����Ŀ¼
     * @param downloadFile
     *            ���ص��ļ�
     * @param saveFile
     *            ���ڱ��ص�·��
     * @throws SftpException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
        log.info("file:{} is download successful" , downloadFile);
    }
    /**
     * �����ļ�
     * @param directory ����Ŀ¼
     * @param downloadFile ���ص��ļ���
     * @return �ֽ�����
     * @throws SftpException
     * @throws IOException
     * @throws Exception
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        log.info("file:{} is download successful" , downloadFile);
        return fileData;
    }

    /**
     * ɾ���ļ�
     *
     * @param directory
     *            Ҫɾ���ļ�����Ŀ¼
     * @param deleteFile
     *            Ҫɾ�����ļ�
     * @throws SftpException
     * @throws Exception
     */
    public void delete(String directory, String deleteFile) throws SftpException{
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * �г�Ŀ¼�µ��ļ�
     *
     * @param directory
     *            Ҫ�г���Ŀ¼
     * @param sftp
     * @return
     * @throws SftpException
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }


    /**
     * �����ļ�������
     * @return �����ַ
     */
    public static String saveFileToLocal(File saveFile,String path){
        return saveFileToLocal(saveFile,path,saveFile.getName());
    }

    /**
     * �����ļ�������
     * @param path ��ŵ�ַ D://upload
     * @param filename �ļ����� photo.jpg
     *   ���:�ļ��������D://upload/photo.jpg
     * @return �����ַ
     */
    public static String saveFileToLocal(File saveFile,String path,String filename){
        String filepath = getAbsolutePath(path,filename);
        System.out.println(filepath);
        File newFile = new File(filepath);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(saveFile);
            os = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] b = new byte[1024*1024];
        int len = -1;
        try {
            while((len = is.read(b))!=-1){
                os.write(b,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null){
                    is.close();
                }
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filepath;
    }

    /**
     * ��þ���·��
     * @param arg
     * @return
     */
    public static String getAbsolutePath(String ... arg){
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for(String str : arg){
            if(count == 0){
                stringBuilder.append(str);
                ++count;
            }else{
                stringBuilder.append("\\").append(str);
            }
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) throws Exception {
        SftpDemo sftp = new SftpDemo("JMS353035sftp", "Tr3sKv56Ld5sq24d", "ftp-1.fuiou.com", 9022);
        sftp.login();
        byte[] buff = sftp.download("check", "20170707_P2P_PWJY_20170707_0000.txt");
        //�����ڱ���
        InputStream byteInputStream = new ByteArrayInputStream(buff);
        //��ȡ��һ��һ�н�����
        BufferedReader br = new BufferedReader(new InputStreamReader(byteInputStream,"GBK"));
        String line1 = null;
        while((line1 = br.readLine()) != null){
            System.out.println(line1+"��һ��");
        }
        //System.out.println(new String(buff,"GBK"));

        //��ǰĿ¼�Ѿ���check
        sftp.upload(".","D://mytext.txt");

        sftp.logout();
    }




}